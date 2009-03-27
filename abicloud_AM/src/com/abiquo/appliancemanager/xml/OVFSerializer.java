package com.abiquo.appliancemanager.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.dmtf.schemas.ovf.envelope._1.EnvelopeType;
import org.dmtf.schemas.ovf.envelope._1.MsgType;

import org.dmtf.schemas.ovf.envelope._1.VirtualHardwareSectionType;
import org.dmtf.schemas.ovf.envelope._1.VirtualSystemType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.appliancemanager.exceptions.XMLException;
import com.abiquo.ovfindex.RepositorySpace;

/**
 * Use JAXB to bind standard OVF-envelope and OVFIndex objects into/from XML documents.<br/>
 * 
 * @see Stax2Factory, where Woodstox is used as StAX implementation of the underlying XML parser.
 * @see ANT build file "jaxb" target, where the binding classes are generated.
 * @TODO XMLStreamWriter can specify the encoding ... useful ?
 * @TODO close writers even when an exception occurs.
 */
public class OVFSerializer
{
    private final static Logger log = LoggerFactory.getLogger(OVFSerializer.class);

    /** Define the allowed objects to be binded from/into the OVF-envelope schema definition. */
    private final JAXBContext contextEnvelope;

    /** Define the allowed objects to be binded form/into the OVFIndex schema definition. */
    private final JAXBContext contextIndex;

    /** Generated factory to create XML elements in OVF-envelope name space. */
    private final org.dmtf.schemas.ovf.envelope._1.ObjectFactory factoryEnvelop;

    /** Generated factory to create XML elements on OVFIndex name space. */
    private final com.abiquo.ovfindex.ObjectFactory factoryIndex;

    /** Specify to marshall process to format the XML document. @TODO.from configuration?. */
    private final static boolean isFormattingWrite = true;

    /**
     * @TODO ensure Validation. Specify to unmarshall process to validate the XML document.
     *       @TODO.from configuration?. private final static boolean isValidatingRead = true;
     */

    /** The singleton instance. */
    private static OVFSerializer instance;

    /**
     * Get the OVFSerializer singelton instance.
     * 
     * @return the OVFSerializer instance or null if it can not be created.
     */
    public static OVFSerializer getInstance()
    {
        if (instance == null)
        {
            try
            {
                instance = new OVFSerializer();
            }
            catch (JAXBException e)
            {
                log.error("OVFSerializer instance can not be created ", e);
            }
        }

        return instance;
    }

    /**
     * Instantiate a new OVFSerializer.
     * 
     * @throws JAXBException, if some JAXB context can not be created.
     */
    private OVFSerializer() throws JAXBException
    {
        contextEnvelope = JAXBContext.newInstance(EnvelopeType.class);
        contextIndex = JAXBContext.newInstance(RepositorySpace.class);
        factoryEnvelop = new org.dmtf.schemas.ovf.envelope._1.ObjectFactory();
        factoryIndex = new com.abiquo.ovfindex.ObjectFactory();
    }

    /**
     * Creates an XML document representing the provided OVF-envelop and write it to output stream.
     * 
     * @param envelope, the object to be binded into an XML document.
     * @param os, the destination of the XML document.
     * @throws XMLException, any XML problem.
     */
    public void writeXML(EnvelopeType envelope, OutputStream os) throws XMLException
    {
        XMLStreamWriter writer;
        Marshaller marshall;

        try
        {
            writer = Stax2Factory.getStreamWriterFactory().createXMLStreamWriter(os);
            marshall = contextEnvelope.createMarshaller();

            if (isFormattingWrite)
            {
                marshall.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
            }

            marshall.marshal(factoryEnvelop.createEnvelope(envelope), writer);

            writer.close();
        }
        catch (JAXBException ea)
        {
            throw new XMLException(ea);
        }
        catch (XMLStreamException ex)
        {
            throw new XMLException(ex);
        }
        // finally writer.close();
    }

    /**
     * Creates an XML document representing an OVF-envelop with the provided virtual system and
     * write it to output stream.
     * 
     * @param virutalSystem, the object inside the envelope to be binded into an XML document.
     * @param os, the destination of the XML document.
     * @throws XMLException, any XML problem.
     */
    public void writeXML(VirtualSystemType virtualSystem, OutputStream os) throws XMLException
    {
        EnvelopeType envelope;

        envelope = factoryEnvelop.createEnvelopeType();
        envelope.setContent(factoryEnvelop.createVirtualSystem(virtualSystem));

        writeXML(envelope, os);
    }

    /**
     * Creates an XML document representing the provided repository space and write it to output
     * stream.
     * 
     * @param repoSpace, the object to be binded into an XML document.
     * @param os, the destination of the XML document.
     * @throws XMLException, any XML problem.
     */
    public void writeXML(RepositorySpace repoSpace, OutputStream os) throws XMLException
    {
        XMLStreamWriter writer;
        Marshaller marshall;

        try
        {
            writer = Stax2Factory.getStreamWriterFactory().createXMLStreamWriter(os);
            marshall = contextIndex.createMarshaller();

            if (isFormattingWrite)
            {
                marshall.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
            }

            marshall.marshal(factoryIndex.createRepositorySpace(repoSpace), writer);

            writer.close();
        }
        catch (JAXBException ea)
        {
            throw new XMLException(ea);
        }
        catch (XMLStreamException ex)
        {
            throw new XMLException(ex);
        }
    }

    /**
     * Read an expected OVF-envelope form the provided source.
     * 
     * @param is, the input stream source where read XML documents.
     * @return the OVF-envelope read from source.
     * @throws XMLException, if it is not an envelope type or any XML problem.
     */
    public EnvelopeType readXMLEnvelope(InputStream is) throws XMLException
    {
        XMLStreamReader reader;
        Unmarshaller unmarshall;
        JAXBElement<EnvelopeType> jaxbEnvelope;

        try
        {
            reader = Stax2Factory.getStreamReaderFactory().createXMLStreamReader(is);
            unmarshall = contextEnvelope.createUnmarshaller();

            jaxbEnvelope = unmarshall.unmarshal(reader, EnvelopeType.class);

            reader.close();
        }
        catch (JAXBException ea)
        {
            throw new XMLException(ea);
        }
        catch (XMLStreamException ex)
        {
            throw new XMLException(ex);
        }

        return jaxbEnvelope.getValue();
    }

    /**
     * Read an expected RepositorySpace form the provided source.
     * 
     * @param is, the input stream source where read XML documents.
     * @return the RepositorySpace read from source.
     * @throws XMLException, if it is not a RepositorySpace type or any XML problem.
     */
    public RepositorySpace readXMLRepositorySpace(InputStream is) throws XMLException
    {
        XMLStreamReader reader;
        Unmarshaller unmarshall;
        JAXBElement<RepositorySpace> jaxbRepo;

        try
        {
            reader = Stax2Factory.getStreamReaderFactory().createXMLStreamReader(is);
            unmarshall = contextIndex.createUnmarshaller();

            jaxbRepo = unmarshall.unmarshal(reader, RepositorySpace.class);

            reader.close();
        }
        catch (JAXBException ea)
        {
            throw new XMLException(ea);
        }
        catch (XMLStreamException ex)
        {
            throw new XMLException(ex);
        }

        return jaxbRepo.getValue();
    }

    /**
     * Create an Envelepo to test
     * 
     * @deprecated first test
     */
    private EnvelopeType createTestEnvelope()
    {
        EnvelopeType enve;

        enve = factoryEnvelop.createEnvelopeType();

        VirtualSystemType virtualSystem = factoryEnvelop.createVirtualSystemType();

        VirtualHardwareSectionType hw = factoryEnvelop.createVirtualHardwareSectionType();
        virtualSystem.getSection().add(factoryEnvelop.createVirtualHardwareSection(hw));

        MsgType msg = factoryEnvelop.createMsgType();
        msg.setValue("hello OVF");

        virtualSystem.setInfo(msg);
        virtualSystem.setId("unid");

        enve.setContent(factoryEnvelop.createContent(virtualSystem));

        return enve;
    }

    /**
     * run a create/write/read first test
     * 
     * @deprecated
     */
    public static void main(String[] args) throws Exception
    {
        final String filePath = "resources/xml/envelopeexample2.xml";
        EnvelopeType envelop;
        OVFSerializer ovfserial = new OVFSerializer();

        envelop = ovfserial.createTestEnvelope();
        System.err.println("created");
        ovfserial.writeXML(envelop, new FileOutputStream(new File(filePath)));
        System.err.println("writed");
        envelop = ovfserial.readXMLEnvelope(new FileInputStream(new File(filePath)));
        System.err.println("readed");

    }

}
