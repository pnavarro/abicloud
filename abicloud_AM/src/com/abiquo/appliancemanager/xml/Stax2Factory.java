package com.abiquo.appliancemanager.xml;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;

import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLOutputFactory2;

import com.ctc.wstx.api.WstxOutputProperties;
import com.ctc.wstx.stax.WstxInputFactory;
import com.ctc.wstx.stax.WstxOutputFactory;

/**
 * Factories to configure the StAX implementation used on XML communication.
 * 
 * @see http://woodstox.codehaus.org/ (4.0 release notes) Default setting of IS_COALESCING *changed
 *      to false* * Default setting of WstxOutputProperties.P_OUTPUT_FIX_CONTENT *changed to false*
 *      * "no prefix" and "no namespace [URI]" have been changed from null value to empty String
 *      ("")
 */
public final class Stax2Factory
{
    /** The singleton instance. */
    private static Stax2Factory instance;

    /** Factory to obtain new StAX XML readers (implements XMLInputFactory). */
    private final WstxInputFactory inputFact;

    /** Factory to obtain new StAX XML writers (implements XMLOutputFactory). */
    private final WstxOutputFactory outputFact;

    /** Enable XMLStreamReader to uses name spaces. */
    private final boolean isNamespaceAware = true;

    /**
     * Property that determines whether Carriage Return (\r) characters are to be escaped when
     * output or not. If enabled, all instances of of character \r are escaped using a character
     * entity (where possible, that is, within CHARACTERS events, and attribute values). Otherwise
     * they are output as is. The main reason to enable this property is to ensure that carriage
     * returns are preserved as is through parsing, since otherwise they will be converted to
     * canonical xml linefeeds (\n), when occuring along or as part of \r\n pair.
     */
    private final boolean isEscapingOutputCR = false;

    /** Configure Woodstox as StAX XML implementation (actually stax2). */
    static
    {
        final String inputFactoryImpl = "com.ctc.wstx.stax.WstxInputFactory";
        final String outputFactoryImpl = "com.ctc.wstx.stax.WstxOutputFactory";
        final String eventFactoryImpl = "com.ctc.wstx.stax.WstxEventFactory";

        System.setProperty("javax.xml.stream.XMLInputFactory", inputFactoryImpl);
        System.setProperty("javax.xml.stream.XMLOutputFactory", outputFactoryImpl);
        System.setProperty("javax.xml.stream.XMLEventFactory", eventFactoryImpl);

    }

    /**
     * Configure and creates the factories instances.
     */
    private Stax2Factory()
    {
        inputFact = (WstxInputFactory) XMLInputFactory.newInstance();
        outputFact = (WstxOutputFactory) XMLOutputFactory.newInstance();

        inputFact.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, isNamespaceAware);
        inputFact.setProperty(XMLInputFactory2.P_REPORT_PROLOG_WHITESPACE, Boolean.FALSE);

        outputFact.setProperty(WstxOutputProperties.P_OUTPUT_ESCAPE_CR, isEscapingOutputCR);
    }

    /**
     * Return the singleton output factory to get XML writers configured to StAX.
     * 
     * @return The output factory.
     */
    public static XMLOutputFactory2 getStreamWriterFactory()
    {
        synchronized (Stax2Factory.class)
        {
            if (instance == null)
            {
                instance = new Stax2Factory();
            }
        }

        return instance.outputFact;
    }

    /**
     * Returns the singleton input factory to get XML readers configured to StAX.
     * 
     * @return The input factory.
     */
    public static XMLInputFactory2 getStreamReaderFactory()
    {
        synchronized (Stax2Factory.class)
        {
            if (instance == null)
            {
                instance = new Stax2Factory();
            }
        }

        return instance.inputFact;
    }

}
