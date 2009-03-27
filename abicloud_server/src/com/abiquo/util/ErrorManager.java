/*
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in 
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 * 
 * Software distributed under the License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 * 
 * The Original Code is available at http://www.abiquo.com/.....
 * 
 * The Initial Developer of the Original Code is Soluciones Grid, S.L. (www.abiquo.com),
 * Consell de Cent 296, Principal 2�, 08007 Barcelona, Spain.
 * 
 * No portions of the Code have been created by third parties. 
 * All Rights Reserved.
 * 
 * Contributor(s): ______________________________________.
 * 
 * Graphical User Interface of this software may be used under the terms
 * of the Common Public Attribution License Version 1.0 (the  "CPAL License", 
 * available at http://cpal.abiquo.com/), in which case the 
 * provisions of CPAL License are applicable instead of those above. In relation 
 * of this portions of the Code, a Legal Notice according to Exhibits A and B of 
 * CPAL Licence should be provided in any distribution of the corresponding Code 
 * to Graphical User Interface.
 */

package com.abiquo.util;

import com.abiquo.util.resources.ResourceManager;
import com.abiquo.util.resources.XMLResourceBundle;
import java.util.Locale;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import com.abiquo.abiserver.pojo.result.BasicResult;

/**
 * @author aodachi
 */
public class ErrorManager
{

    private static ErrorManager singletonObject;

    /**
     * Contains the properties of this ErrorManager which is loaded from a .properties file.
     */
    private Properties properties;

    /**
     * The prefix for the error codes generated
     */
    private String prefix;

    private int serialCounter;

    /**
     * It could happen that the access method may be called twice from 2 different classes at the
     * same time and hence more than one object being created. This could violate the design patter
     * principle. In order to prevent the simultaneous invocation of the getter method by 2 threads
     * or classes simultaneously we add the synchronized keyword to the method declaration
     * 
     * @param args
     * @return
     */
    public static synchronized ErrorManager getInstance(String... args)
    {

        if (ErrorManager.singletonObject == null)
            ErrorManager.singletonObject = new ErrorManager(args.length > 0 ? args[0] : "");

        return ErrorManager.singletonObject;

    }

    private ErrorManager(String prefix)
    {
        this.prefix = prefix;

        this.properties = new ResourceManager(ErrorManager.class).getProperties();

        this.serialCounter = 0;
    }

    /**
     * We can still be able to create a copy of the Object by cloning it using the Object’s clone
     * method. This can be done as shown below ErrorManager clonedObject = (ErrorManager)
     * obj.clone(); This again violates the Singleton Design Pattern's objective. So to deal with
     * this we need to override the Object’s clone method which throws a CloneNotSupportedException
     * exception.
     * 
     * @return
     * @throws java.lang.CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        throw new CloneNotSupportedException("This is a singleton class and hence can only have one object");
    }

    /**
     * Returns the name of the error associated with the supplied error code.
     * 
     * @param errorCode error code whose name is to be returned
     * @return String containing the name of the error associated to the error code passed as an
     *         argument.
     */
    public String getErrorName(String errorCode)
    {
        return this.prefix + "-" + this.properties.getProperty(errorCode);
    }

    // With the prefix appended
    public String getErrorCode(String errorCode)
    {
        return this.prefix + "-" + errorCode;
    }

    public synchronized String generateErrorSerialNumber()
    {

        this.serialCounter++;

        return "00" + this.serialCounter;
    }

    /**
     * @param resourceManager a reference to a ResourceManager object
     * @param faultResult a BasicResult object
     * @param bundleBaseName basename of a resource bundle
     * @param msgs optional array of strings that will be used to replace place holder in message
     *            strings in the resource bundle
     */
    public void reportError(ResourceManager resourceManager, BasicResult faultResult,
        String bundleBaseName, String... msgs)
    {
        this.reportError(resourceManager, faultResult, bundleBaseName, null, 0, msgs);
    }

    public void reportError(ResourceManager resourceManager, BasicResult faultResult,
        String bundleBaseName, Integer idVirtualAppliance, String... msgs)
    {
        this.reportError(resourceManager, faultResult, bundleBaseName, null, idVirtualAppliance,
            msgs);
    }

    /**
     * @param resourceManager
     * @param faultResult
     * @param bundleBaseName
     * @param exception
     * @param msgs
     */
    public void reportError(ResourceManager resourceManager, BasicResult faultResult,
        String bundleBaseName, Exception exception, String... msg)
    {
        this.reportError(resourceManager, faultResult, bundleBaseName, exception, 0, msg);
    }

    // reportError(resourceManager,faultResult,"onFaultAuthorization.needsAuthorization",methodName);

    // This reports an error for a virtual machine
    public void reportError(ResourceManager resourceManager, BasicResult faultResult,
        String bundleBaseName, Exception exception, Integer idVirtualAppliance, String... msgs)
    {

        ResourceBundle bundle = resourceManager.getResourceBundle();

        String errorCode = bundle.getString(bundleBaseName + ".errorCode");
        String errorName = this.properties.getProperty(errorCode);

        Object objLogMsg = null;
        Object objExtraMsg = null;

        // We use handleGetObject as the the attribute [KEY_BASE].logMsg is not always defined
        if (bundle instanceof PropertyResourceBundle)
        {

            PropertyResourceBundle pBundle = (PropertyResourceBundle) bundle;

            objLogMsg = pBundle.handleGetObject(bundleBaseName + ".logMsg");
            objExtraMsg = pBundle.handleGetObject(bundleBaseName + ".extraMsg");

        }
        else if (bundle instanceof XMLResourceBundle)
        {

            XMLResourceBundle xmlBundle = (XMLResourceBundle) bundle;

            objLogMsg = xmlBundle.handleGetObject(bundleBaseName + ".logMsg");
            objExtraMsg = xmlBundle.handleGetObject(bundleBaseName + ".extraMsg");

        }

        String logMsg = (objLogMsg != null) ? objLogMsg.toString() : "";
        String extraMsg = (objExtraMsg != null) ? objExtraMsg.toString() + "\n" : "";

        // This is will be change based on the language selected by the client
        String bundleName = Locale.getDefault().toString();
        String errorMsg = null, contactInstructions = null;

        if (this.properties.containsKey(bundleName + ".errorMsg"))
        {
            errorMsg = this.properties.getProperty(bundleName + ".errorMsg");
            contactInstructions =
                this.properties.getProperty(bundleName + ".errorMsg.contactInstructions");
        }
        else
        {
            this.properties.getProperty("errorMsg");
            contactInstructions = this.properties.getProperty("errorMsg.contactInstructions");
        }
        AbiError error =
            new AbiError(this.prefix + errorCode,
                errorName,
                errorMsg,
                extraMsg,
                contactInstructions,
                logMsg,
                msgs,
                this.generateErrorSerialNumber(),
                exception,
                idVirtualAppliance);

        error.handleError(faultResult);

    }

    @Override
    public String toString()
    {
        return com.abiquo.util.ToString.toString(this);
    }

    public static void main(String[] args)
    {
        ErrorManager eM = ErrorManager.getInstance();
        ResourceManager resourceManager =
            new ResourceManager(com.abiquo.abiserver.commands.VirtualApplianceCommand.class);
        System.out.println(resourceManager);
        com.abiquo.abiserver.pojo.result.BasicResult b =
            new com.abiquo.abiserver.pojo.result.BasicResult();
        eM.reportError(resourceManager, b, "editVirtualAppliance", 1);
    }

}
