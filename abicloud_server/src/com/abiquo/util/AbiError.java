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
 * Consell de Cent 296 principal 2�, 08007 Barcelona, Spain.
 * No portions of the Code have been created by third parties. 
 * All Rights Reserved.
 * 
 * Contributor(s): ______________________________________.
 * 
 * Graphical User Interface of this software may be used under the terms
 * of the Common Public Attribution License Version 1.0 (the  "CPAL License",
 * available at http://cpal.abiquo.com), in which case the provisions of CPAL
 * License are applicable instead of those above. In relation of this portions 
 * of the Code, a Legal Notice according to Exhibits A and B of CPAL Licence 
 * should be provided in any distribution of the corresponding Code to Graphical
 * User Interface.
 */
package com.abiquo.util;

import com.abiquo.abiserver.business.hibernate.pojohb.virtualappliance.LogHB;
import com.abiquo.abiserver.business.hibernate.util.HibernateUtil;
import java.text.MessageFormat;

import com.abiquo.abiserver.pojo.result.BasicResult;

import com.abiquo.abiserver.pojo.virtualappliance.Log;
import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;
import java.util.Date;
import java.util.Locale;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @author aodachi This class holds the information on an error generated during the use of the
 *         application
 */
public class AbiError
{

    private static final Logger logger = Logger.getLogger(AbiError.class);

    private Integer idVirtualAppliance;

    private String errorCode;

    private String errorName;

    private String errorMsg;

    private String extraMsg;

    private String logMsgTemplate;

    private Exception e;

    private String errorID;

    private String[] msgs;

    private String contactInstructions;

    private static final SimpleDateFormat sdf =
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK);

    /**
     * @param errorCode the error code of the type of error that was generated
     * @param errorName the associated name with the above error code
     * @param errorMsg the standard error message
     * @param extraMsg customized error message that provides more information on the generated
     *            error
     * @param contactInstructions Message advising the user on how to go about resolving the problem
     *            that has arisen
     * @param logMsgTemplate a message template used for reporting messages to the the log file
     * @param msgs an array of strings containing values for which the place holders (if any) in
     *            <code>logMsgTemplate</code> will be replaced
     * @param errorID the unique id associated with the generated error
     * @param e a reference to an <code>Exception</code> object if an Exception was thrown
     * @param idVirtualAppliance the id of the virtual machine - can be null if the error if the
     *            operation does not involve a virtualAppliance
     */
    public AbiError(String errorCode, String errorName, String errorMsg, String extraMsg,
        String contactInstructions, String logMsgTemplate, String[] msgs, String errorID,
        Exception e, Integer idVirtualAppliance)
    {

        this.errorCode = errorCode;
        this.errorName = errorName;
        this.errorMsg = errorMsg;
        this.errorID = errorID;
        this.e = e;
        this.logMsgTemplate = logMsgTemplate;
        this.msgs = msgs;
        this.extraMsg = extraMsg;
        this.contactInstructions = contactInstructions;
        this.idVirtualAppliance = idVirtualAppliance;

    }

    /**
     * Formats the message that will be sent or stored in the log file and database
     * 
     * @return
     */
    private String getMessage(boolean appendContactInstructions)
    {

        String msg = this.errorMsg;

        if (appendContactInstructions)
            msg += "\n" + this.contactInstructions;

        return MessageFormat.format(msg, new Object[] {this.errorName, this.extraMsg,
        this.errorCode, this.errorID,});
    }

    /**
     * Sets the message of a <code>BasicResult</code> object, its success to <code>false</code> and
     * also logs the error Message.
     * 
     * @param result
     * @param error
     */
    public final void handleError(BasicResult result)
    {

        this.logMessage();

        if (result != null)
        {
            result.setSuccess(false);
            result.setMessage(this.getMessage(true));
        }

    }

    /**
     * Logs the error to the log file and also stores the error in the database if the error
     * occurred during a modification,creation or deletion of a VirtuaAppliance
     */
    public void logMessage()
    {

        String logMessage = MessageFormat.format(this.logMsgTemplate, (Object[]) this.msgs);
        logMessage += "[Error code:" + this.errorCode + " Error ID:" + this.errorID + "].";

        logger.error(logMessage, this.e);

        if (this.idVirtualAppliance != null && this.idVirtualAppliance > 0)
        {

            // Now save the message in the database
            Log virtualApplicanceLog = new Log();
            Session session = null;
            Transaction transaction = null;

            try
            {

                session = HibernateUtil.getSession();
                transaction = session.beginTransaction();

                LogHB virtualApplianceLogHB = (LogHB) virtualApplicanceLog.toPojoHB();
                virtualApplianceLogHB.setIdVirtualAppliance(this.idVirtualAppliance);
                virtualApplianceLogHB.setDescription(this.getMessage(false));
                virtualApplianceLogHB.setLogDate(new Date());

                System.out.println(this.getMessage(false));

                session.save(virtualApplianceLogHB);

                transaction.commit();

            }
            catch (Exception e)
            {

                logger.error("SQL Error", e);

                if (transaction != null && transaction.isActive())
                    transaction.rollback();

            }
        }

    }

    public String toString()
    {
        return com.abiquo.util.ToString.toString(this);
    }

}
