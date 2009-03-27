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
package com.abiquo.abiserver.commands;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.abiquo.abiserver.business.authentication.SessionUtil;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.HypervisorHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.PhysicalmachineHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.StateHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.VirtualmachineHB;
import com.abiquo.abiserver.business.hibernate.pojohb.user.EnterpriseHB;
import com.abiquo.abiserver.business.hibernate.pojohb.user.UserHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualappliance.NodeHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualappliance.NodeNetworkHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualappliance.NodeStorageHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualappliance.NodeVirtualImageHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualappliance.VirtualDataCenterHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualappliance.VirtualappHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualimage.VirtualimageHB;
import com.abiquo.abiserver.business.hibernate.util.HibernateUtil;
import com.abiquo.abiserver.pojo.authentication.UserSession;
import com.abiquo.abiserver.pojo.infrastructure.HyperVisor;
import com.abiquo.abiserver.pojo.infrastructure.PhysicalMachine;
import com.abiquo.abiserver.pojo.infrastructure.State;
import com.abiquo.abiserver.pojo.infrastructure.VirtualMachine;
import com.abiquo.abiserver.pojo.result.BasicResult;
import com.abiquo.abiserver.pojo.result.DataResult;
import com.abiquo.abiserver.pojo.user.Enterprise;
import com.abiquo.abiserver.pojo.virtualappliance.Log;
import com.abiquo.abiserver.pojo.virtualappliance.Node;
import com.abiquo.abiserver.pojo.virtualappliance.NodeNetwork;
import com.abiquo.abiserver.pojo.virtualappliance.NodeStorage;
import com.abiquo.abiserver.pojo.virtualappliance.NodeType;
import com.abiquo.abiserver.pojo.virtualappliance.NodeVirtualImage;
import com.abiquo.abiserver.pojo.virtualappliance.VirtualAppliance;
import com.abiquo.abiserver.pojo.virtualappliance.VirtualDataCenter;
import com.abiquo.abiserver.pojo.virtualimage.VirtualImage;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.abiquo.abiserver.abicloudws.VirtualApplianceWS;
import com.abiquo.abiserver.scheduler.SchedulerException;
import com.abiquo.abiserver.scheduler.ranking.MinFitRankingScheduler;

import com.abiquo.util.resources.ResourceManager;

import flex.messaging.io.ArrayCollection;

/**
 * This command collects all actions related to Virtual Appliances
 * 
 * @author abiquo
 */
public class VirtualApplianceCommand extends BasicCommand
{
    /** The resource manager object. */
    private static final ResourceManager resourceManager =
        new ResourceManager(VirtualApplianceCommand.class);

    /** The scheduler */
    private static MinFitRankingScheduler scheduler = new MinFitRankingScheduler();

    /** The logger object */
    private final static Logger logger = Logger.getLogger(VirtualApplianceCommand.class);

    /**
     * Retrieves a list of Virtual Appliances that belong to the same Enterprise The
     * VirtualAppliance retrieved will not contain their Node list, for performance purposes It will
     * also return those Virtual Appliance marked as public
     * 
     * @param enterprise The Enterprise to retrieve the VirtualAppliance list
     * @return a DataResult<ArrayList<VirtualAppliance>> object with the VirtualAppliance that belong
     *         to the given enterprise.
     */
    protected DataResult<ArrayList<VirtualAppliance>> getVirtualAppliancesByEnterprise(
        Enterprise enterprise)
    {
        DataResult<ArrayList<VirtualAppliance>> dataResult =
            new DataResult<ArrayList<VirtualAppliance>>();

        Session session = null;
        Transaction transaction = null;

        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Retrieving the enterprise
            EnterpriseHB enterpriseHB =
                (EnterpriseHB) session.get(EnterpriseHB.class, enterprise.getId());

            // Retrieving the VirtualAppliance list (without nodes!)
            Disjunction enterpriseORpublic = Restrictions.disjunction();
            enterpriseORpublic.add(Restrictions.eq("enterpriseHB", enterpriseHB));
            enterpriseORpublic.add(Restrictions.eq("public_", 1));
            ArrayList<VirtualappHB> virtualappHBList =
                (ArrayList<VirtualappHB>) session.createCriteria("VirtualappHB").add(
                    enterpriseORpublic).list();

            ArrayList<VirtualAppliance> virtualApplianceList = new ArrayList<VirtualAppliance>();
            for (VirtualappHB virtualappHB : virtualappHBList)
            {
                virtualApplianceList.add((VirtualAppliance) virtualappHB.toPojo());
            }

            // Building result
            dataResult.setSuccess(true);
            dataResult.setData(virtualApplianceList);
            dataResult.setMessage(VirtualApplianceCommand.resourceManager
                .getMessage("getVirtualAppliancesByEnterprise.success"));

            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManager, dataResult,
                "getVirtualAppliancesByEnterprise", e);
        }

        return dataResult;
    }

    /**
     * Given a VirtualAppliance, retrieves its node list.
     * 
     * @param virtualAppliance The VirtualAppliance to retrieve the nodes.
     * @return a DataResult<ArrayList<Node>> object, containing the virtualAppliance's Nodes.
     */
    protected DataResult<ArrayList<Node>> getVirtualApplianceNodes(VirtualAppliance virtualAppliance)
    {
        DataResult<ArrayList<Node>> dataResult = new DataResult<ArrayList<Node>>();

        Session session = null;
        Transaction transaction = null;

        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            VirtualappHB virtualAppHB =
                (VirtualappHB) session.get("VirtualappExtendedHB", virtualAppliance.getId());

            ArrayList<Node> nodeList = ((VirtualAppliance) virtualAppHB.toPojo()).getNodes();

            // Building result
            dataResult.setSuccess(true);
            dataResult.setData(nodeList);
            dataResult.setMessage(VirtualApplianceCommand.resourceManager
                .getMessage("getVirtualApplianceNodes.success"));

            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManager, dataResult, "getVirtualApplianceNodes",
                e, virtualAppliance.getId());
        }

        return dataResult;
    }

    /**
     * Returns the Virtual Appliances that belongs to the user who called this method, or those that
     * are public (VirtualAppliance.isPublic)
     * 
     * @param userSession the user session.
     * @return A DataResult object, containing an ArraList of VirtualAppliance
     */
    protected DataResult<ArrayList<VirtualAppliance>> getVirtualAppliancesByUser(
        UserSession userSession)
    {
        DataResult<ArrayList<VirtualAppliance>> dataResult =
            new DataResult<ArrayList<VirtualAppliance>>();

        Session session = null;
        Transaction transaction = null;

        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Getting the user who is asking for the Virtual Appliances
            UserHB userHB =
                (UserHB) session.createCriteria(UserHB.class).add(
                    Restrictions.eq("user", userSession.getUser())).uniqueResult();

            // Retrieving the user VirtualAppliance list (without nodes!
            Disjunction userDisjuntion = Restrictions.disjunction();
            userDisjuntion.add(Restrictions.eq("userHBByIdUserCreation", userHB));
            ArrayList<VirtualappHB> virtualappHBList =
                (ArrayList<VirtualappHB>) session.createCriteria("VirtualappHB")
                    .add(userDisjuntion).list();

            ArrayList<VirtualAppliance> virtualApplianceList = new ArrayList<VirtualAppliance>();
            for (VirtualappHB virtualappHB : virtualappHBList)
            {
                virtualApplianceList.add((VirtualAppliance) virtualappHB.toPojo());
            }

            // Building result
            dataResult.setSuccess(true);
            dataResult.setData(virtualApplianceList);
            dataResult.setMessage(VirtualApplianceCommand.resourceManager
                .getMessage("getVirtualAppliancesByUser.success"));

            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManager, dataResult,
                "getVirtualAppliancesByUser", e);
        }

        return dataResult;
    }

    /**
     * Creates a new Virtual Appliance, that belongs to the user who called this method
     * 
     * @param userSession the user connected.
     * @param virtualAppliance object
     * @return A DataResult object containing the VirtualAppliance created in the Data Base
     */
    protected DataResult<VirtualAppliance> createVirtualAppliance(UserSession userSession,
        VirtualAppliance virtualAppliance)
    {

        DataResult<VirtualAppliance> dataResult = new DataResult<VirtualAppliance>();

        VirtualappHB virtualAppHBPojo = null;
        Session session = null;
        Transaction transaction = null;

        // Variable to hold the id of the virtual appliance - this will be used to report an error
        // to the database
        Integer virtualApplianceId = null;

        try
        {
            // Getting the user
            UserHB user = SessionUtil.findUserHBByName(userSession.getUser());

            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            virtualAppHBPojo = (VirtualappHB) virtualAppliance.toPojoHB();

            StateHB stateNotdeployed = new StateHB();
            stateNotdeployed.setIdState(State.NOT_DEPLOYED);

            virtualAppHBPojo.setState(stateNotdeployed);
            virtualAppHBPojo.setUserHBByIdUserCreation(user);
            virtualAppHBPojo.setCreationDate(new Date());

            // Saving the data
            session.save("VirtualappHB", virtualAppHBPojo);

            // Set the virtualApplianceId
            virtualApplianceId = virtualAppHBPojo.getIdVirtualApp();

            // Recovering the best information
            virtualAppliance =
                (VirtualAppliance) ((VirtualappHB) session.get("VirtualappExtendedHB",
                    virtualApplianceId)).toPojo();

            // Updating dataResult information
            dataResult.setData(virtualAppliance);
            dataResult.setMessage(VirtualApplianceCommand.resourceManager
                .getMessage("createVirtualAppliance.success"));
            dataResult.setSuccess(true);

            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null & transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManager, dataResult, "createVirtualAppliance", e,
                virtualApplianceId);

            return dataResult;
        }

        return dataResult;

    }

    /**
     * Private helper to convert a infrastructure pojo node to hibernate pojo and save it to the DB
     * 
     * @param session the hibernate session
     * @param node the node created from the pojo
     * @param virtualAppliance virtual appliance of the note
     * @return
     * @throws SchedulerException
     */
    private NodeHB createNodePojo(Session session, Node node, VirtualAppliance virtualAppliance)
        throws SchedulerException
    {
        NodeHB nodePojo = new NodeHB();

        // Adding generic node information
        nodePojo.setPosX(node.getPosX());
        nodePojo.setPosY(node.getPosY());
        nodePojo.setIdVirtualApp(virtualAppliance.getId());

        // For each kind of node...
        if (node.getNodeType().getId() == NodeType.VIRTUALIMAGE)
            nodePojo =
                createNodeVirtualImagePojo(session, (NodeVirtualImageHB) ((NodeVirtualImage) node)
                    .toPojoHB(), virtualAppliance);
        if (node.getNodeType().getId() == NodeType.STORAGE)
            nodePojo =
                createNodeStoragePojo(session, (NodeStorageHB) ((NodeStorage) node).toPojoHB(),
                    virtualAppliance);
        if (node.getNodeType().getId() == NodeType.NETWORK)
            nodePojo =
                createNodeNetworkPojo(session, (NodeNetworkHB) ((NodeNetwork) node).toPojoHB(),
                    virtualAppliance);

        // Updating the visual nodes
        node.setId(nodePojo.getIdNode());

        return nodePojo;
    }

    /**
     * This method update information of a NodeVirtualImage This method runs extra process to call
     * when user add new nodes.
     * 
     * @param session of the transaction
     * @param nodeVIPojo the VirtualImage Node
     * @param virtualAppliance the virtualApp object
     * @return the node updated
     * @throws SchedulerException if it happens something
     */
    private NodeHB createNodeVirtualImagePojo(Session session, NodeVirtualImageHB nodeVIPojo,
        VirtualAppliance virtualAppliance) throws SchedulerException
    {
        VirtualimageHB virtualImage = nodeVIPojo.getVirtualImageHB();

        // Creating virtual image pojo
        VirtualimageHB vi =
            (VirtualimageHB) session.get(VirtualimageHB.class, virtualImage.getIdImage());

        // If virtual appliance has been started - create virtual machine for the current node
        if (virtualAppliance.getState().getId() == State.RUNNING)
            this.createNodeVirtualMachine(session, vi, nodeVIPojo);

        nodeVIPojo.setVirtualImageHB(vi);

        session.save("NodeVirtualImageHB", nodeVIPojo);

        return nodeVIPojo;
    }

    /**
     * This method update information of a Storage node. This method runs extra process to call when
     * user add new nodes.
     * 
     * @param session of the transaction
     * @param nodeStoragePojo the node object
     * @param virtualAppliance the virtual appliance of the node
     * @return the node object
     * @throws SchedulerException if it happens something
     */
    private NodeHB createNodeStoragePojo(Session session, NodeStorageHB nodeStoragePojo,
        VirtualAppliance virtualAppliance) throws SchedulerException
    {
        // Now... Nothing to do

        session.save("NodeStorageHB", nodeStoragePojo);

        return nodeStoragePojo;
    }

    /**
     * This method update the information of a Network node This method runs extra process to call
     * when user add new nodes.
     * 
     * @param session of the transaction
     * @param nodeNPojo the node object
     * @param virtualAppliance
     * @return the node object
     * @throws SchedulerException if it happens something
     */
    private NodeHB createNodeNetworkPojo(Session session, NodeNetworkHB nodeNPojo,
        VirtualAppliance virtualAppliance) throws SchedulerException
    {
        // Now... Nothing to do

        session.save("NodeNetworkHB", nodeNPojo);

        return nodeNPojo;
    }

    /**
     * Create a virtual for a particular node
     * 
     * @param session the hibernate session
     * @param virtualimageHB
     * @param nodePojo
     * @throws com.abiquo.abiserver.scheduler.SchedulerException
     */
    private void createNodeVirtualMachine(Session session, VirtualimageHB virtualimageHB,
        NodeVirtualImageHB nodePojo) throws SchedulerException
    {

        String dataCenterName = this.abiConfig.getDataCenterName();

        VirtualMachine virtualMachine;
        virtualMachine =
            scheduler.selectMachine((VirtualImage) virtualimageHB.toPojo(), dataCenterName);
        VirtualmachineHB virtualMachineHB = (VirtualmachineHB) virtualMachine.toPojoHB();
        nodePojo.setVirtualMachineHB(virtualMachineHB);

        session.save(virtualMachineHB);

    }

    /**
     * Modifies the information of a VirtualAppliance that already exists in the Data Base
     * 
     * @param userSession the active user.
     * @param virtualAppliance to modify
     * @return A DataResult object, containing a list of nodes modified
     */
    protected DataResult<VirtualAppliance> editVirtualAppliance(UserSession userSession,
        VirtualAppliance virtualAppliance)
    {
        DataResult<VirtualAppliance> dataResult = new DataResult<VirtualAppliance>();

        BasicResult basicResult = new BasicResult();
        VirtualApplianceWS virtualApplianceWs;
        VirtualappHB virtualappHBPojo = null;
        Session session = null;
        Transaction transaction = null;
        List<Node> updatedNodes = null;

        // The VirtualAppliance's state that user sent
        State originalVirtualApplianceState = virtualAppliance.getState();

        // Before start editing a VirtualAppliance, we must check that it is not
        // already being edited by another user
        DataResult<VirtualAppliance> currentStateAndAllow;
        try
        {
            currentStateAndAllow = checkVirtualApplianceState(virtualAppliance);
        }
        catch (Exception e)
        {
            // There was an error while checking the current state of the VirtualAppliance. We can
            // not
            // edit it
            this.errorManager.reportError(resourceManager, dataResult, "editVirtualAppliance", e,
                virtualAppliance.getId());
            dataResult.setData(virtualAppliance);
            return dataResult;
        }

        if (currentStateAndAllow.getSuccess())
        {
            // Now the VirtualAppliance is locked for other users, and we can safely edit it
            try
            {
                UserHB userHB = SessionUtil.findUserHBByName(userSession.getUser());

                session = HibernateUtil.getSession();
                transaction = session.beginTransaction();

                virtualApplianceWs = new VirtualApplianceWS();

                // Recovering the virtualApp information
                virtualappHBPojo =
                    (VirtualappHB) session.get("VirtualappExtendedHB", virtualAppliance.getId());

                virtualappHBPojo.setLastModificationDate(new Date());
                virtualappHBPojo.setUserHBByIdUserLastModification(userHB);
                virtualappHBPojo.setName(virtualAppliance.getName());
                virtualappHBPojo.setPublic_((virtualAppliance.getIsPublic()) ? 1 : 0);
                virtualappHBPojo.setVirtualDataCenterHB((VirtualDataCenterHB) virtualAppliance
                    .getVirtualDataCenter().toPojoHB());
                virtualappHBPojo.setNodeConnections(virtualAppliance.getNodeConnections());

                if (virtualAppliance.getNodes() != null)
                {
                    updatedNodes =
                        updateVirtualAppliancePojo(session, virtualappHBPojo, virtualAppliance);

                    // Update the virtual appliance with the present nodes
                    ArrayList<Node> newNodes = new ArrayList<Node>(updatedNodes);
                    virtualAppliance.setNodes(newNodes);
                    basicResult = virtualApplianceWs.editVirtualAppliance(virtualAppliance);
                }
                else
                {
                    session.update("VirtualappHB", virtualappHBPojo);
                    basicResult.setSuccess(true);
                }

                if (basicResult.getSuccess())
                {
                    if (originalVirtualApplianceState.getId() == State.RUNNING)
                    {
                        // The current Transaction is passed to updateStateInDB
                        // VirtualAppliance unlock will be performed here too
                        dataResult =
                            this.updateStateInDB(virtualAppliance, originalVirtualApplianceState,
                                transaction);
                        if (dataResult.getSuccess())
                        {
                            transaction.commit();
                            // Returning the present state of the virtualappliance from DB
                            session = HibernateUtil.getSession();
                            transaction = session.beginTransaction();
                            VirtualappHB virtualAppPojo =
                                (VirtualappHB) session.get("VirtualappExtendedHB", virtualAppliance
                                    .getId());
                            dataResult.setData((VirtualAppliance) virtualAppPojo.toPojo());
                            return dataResult;
                        }
                        // TODO else????
                    }
                    else
                    {
                        // Perform an unlock of the VirtualAppliance
                        virtualappHBPojo.setState((StateHB) originalVirtualApplianceState
                            .toPojoHB());
                        session.update("VirtualappHB", virtualappHBPojo);
                    }

                    // Check the result again in case updateStateInDB was called
                    if (basicResult.getSuccess())
                    {
                        transaction.commit();
                        dataResult.setData(virtualAppliance);
                        dataResult.setMessage(basicResult.getMessage());
                        dataResult.setSuccess(basicResult.getSuccess());
                    }
                }// TODO A else case should be here, to properly inform user that an error happened
                // and make an transaction.commit()

            }
            catch (Exception e)
            {
                if (transaction != null & transaction.isActive())
                    transaction.rollback();

                this.errorManager.reportError(resourceManager, dataResult, "editVirtualAppliance",
                    e, virtualAppliance.getId());
            }
        }
        else
        {
            // This VirtualAppliance is being used and can not be edited now
            // We inform user of this and return the virtualAppliance with the current state
            dataResult.setSuccess(false);
            dataResult.setMessage(VirtualApplianceCommand.resourceManager
                .getMessage("editVirtualAppliance.modifiedByOther"));
            dataResult.setData(currentStateAndAllow.getData());
        }

        return dataResult;
    }

    /**
     * Private helper to update the virtualAppliances
     * 
     * @param session the hibernate session
     * @param virtualappHBPojo the virtual appliance pojo to update
     * @param virtualAppliance virtualApp to update
     * @return a Node list of the virtual appliance
     * @throws SchedulerException
     */
    private List<Node> updateVirtualAppliancePojo(Session session, VirtualappHB virtualappHBPojo,
        VirtualAppliance virtualAppliance) throws SchedulerException
    {

        Set<NodeHB> nodesPojoList = virtualappHBPojo.getNodesHB();

        List<Node> nodesList = virtualAppliance.getNodes();
        List<Node> updatenodesList = new ArrayList<Node>();
        for (Node node : nodesList)
        {
            switch (node.getModified())
            {
                case Node.NODE_ERASED:
                    NodeHB nodesPojo = (NodeHB) session.get(NodeHB.class, node.getId());
                    session.delete(nodesPojo);
                    break;
                case Node.NODE_MODIFIED:
                    NodeHB nodeModified = (NodeHB) session.get(NodeHB.class, node.getId());
                    nodeModified.setPosX(node.getPosX());
                    nodeModified.setPosY(node.getPosY());
                    nodeModified.setName(node.getName());
                    session.update(nodeModified);
                    updatenodesList.add(node);
                    break;
                case Node.NODE_NEW:
                    NodeHB newNode = createNodePojo(session, node, virtualAppliance);
                    nodesPojoList.add(newNode);
                    // Setting the ID for the new ID
                    node = (Node) newNode.toPojo();
                    updatenodesList.add(node);
                    break;
                case Node.NODE_NOT_MODIFIED:
                    updatenodesList.add(node);
                    break;
                default:
                    break;
            }
        }
        session.update("VirtualappExtendedHB", virtualappHBPojo);

        return updatenodesList;

    }

    /**
     * Deletes a VirtualAppliance that exists in the Data Base
     * 
     * @param virtualAppliance the virtualApp to delete
     * @return a BasicResult object, containing success = true if the deletion was successful
     */
    protected BasicResult deleteVirtualAppliance(VirtualAppliance virtualAppliance)
    {
        BasicResult basicResult = new BasicResult();

        basicResult.setSuccess(true);

        VirtualappHB virtualappHBPojo = null;
        Session session = null;
        Transaction transaction = null;

        Integer virtualApplianceId = virtualAppliance.getId();
        try
        {
            // Deletes only if the resource is deployed
            if (virtualAppliance.getState().getId() != State.NOT_DEPLOYED)
            {
                basicResult = shutdownVirtualAppliance(virtualAppliance);
            }

            if (basicResult.getSuccess())
            {
                session = HibernateUtil.getSession();
                transaction = session.beginTransaction();
                virtualappHBPojo =
                    (VirtualappHB) session.get("VirtualappExtendedHB", virtualApplianceId);

                // Deleting the virtual appliance
                session.delete("VirtualappHB", virtualappHBPojo);

                transaction.commit();
            }
        }
        catch (Exception e)
        {
            if (transaction != null & transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManager, basicResult, "deleteVirtualAppliance",
                e, virtualApplianceId);
        }

        return basicResult;
    }

    /**
     * Performs a "Start" action in the Virtual Machine
     * 
     * @param virtualAppliance virtualApp to start
     * @return a DataResult object, with a VirtualAppliance object with all its virtual machines
     *         created
     */
    protected DataResult<VirtualAppliance> startVirtualAppliance(VirtualAppliance virtualAppliance)
    {

        DataResult<VirtualAppliance> dataResult = new DataResult<VirtualAppliance>();

        BasicResult basicResult = null;
        VirtualApplianceWS virtualApplianceWs;

        // Saving the state of the virtual appliance sent by the user
        State oldState = virtualAppliance.getState();

        // Checking the current state of the virtual appliance
        DataResult<VirtualAppliance> currentStateAndAllow;

        Integer virtualApplianceId = virtualAppliance.getId();
        try
        {
            currentStateAndAllow = checkVirtualApplianceState(virtualAppliance);
        }
        catch (Exception e)
        {
            this.errorManager.reportError(resourceManager, dataResult, "startVirtualAppliance", e,
                virtualApplianceId);
            dataResult.setData(virtualAppliance);// dataResult.setData(oldState);
            return dataResult;
        }

        if (currentStateAndAllow.getSuccess())
        {
            // Create virtual machines for each node before calling the web service
            // Traverse node
            // virtualAppliance.
            try
            {
                this.createVirtualMachines(virtualAppliance, dataResult);
            }
            catch (Exception e1)
            {
                this.errorManager.reportError(resourceManager, dataResult, "createVirtualMachines",
                    e1, virtualApplianceId);
                return dataResult;
            }
            try
            {
                // Calling the webservice
                virtualApplianceWs = new VirtualApplianceWS();
                basicResult = virtualApplianceWs.startVirtualAppliance(virtualAppliance);

            }
            catch (Exception e)
            {
                this.errorManager.reportError(resourceManager, dataResult, "startVirtualAppliance",
                    e, virtualApplianceId);
                deleteVirtualMachines(virtualAppliance, dataResult);
                // Leaving the virtual appliance with its old state
                virtualAppliance = updateStateInDB(virtualAppliance, oldState).getData();

                dataResult.setData(virtualAppliance);
                return dataResult;
            }

            dataResult.setMessage(basicResult.getMessage());
            dataResult.setSuccess(basicResult.getSuccess());
            if (basicResult.getSuccess())
            {
                State newState = new State();
                newState.setId(State.RUNNING);
                virtualAppliance = updateStateInDB(virtualAppliance, newState).getData();
                dataResult.setData(virtualAppliance);

                // return
            }
            else
            {
                // There was a problem changing the state of the virtual appliance
                // We leave it with its old state
                deleteVirtualMachines(virtualAppliance, dataResult);
                virtualAppliance = updateStateInDB(virtualAppliance, oldState).getData();
                dataResult.setData(virtualAppliance); // dataResult.setData(oldState);
            }

            return dataResult;
        }
        else
        {
            // The Virtual Appliance is being used by other user, or it is not up to date.
            // We inform the new state
            dataResult.setSuccess(true);
            dataResult.setMessage(VirtualApplianceCommand.resourceManager
                .getMessage("startVirtualAppliance.modifiedByOther"));
            dataResult.setData(currentStateAndAllow.getData());

            return dataResult;
        }
    }

    /**
     * This method recover the node list of a virtualAppliance IT's used to support third party
     * methods (It doesn't create a session)
     * 
     * @throws SchedulerException when something occurs
     */
    private ArrayList<Node> getNodesFromVirtualApp(Session session, VirtualAppliance vApp)
        throws SchedulerException
    {
        // Maybe vApp has the nodes information
        ArrayList<Node> nodes = vApp.getNodes();

        // If not... recover the information
        if (nodes == null)
        {
            nodes = new ArrayList<Node>();
            VirtualappHB vAppHB = (VirtualappHB) vApp.toPojoHB();
            Disjunction virtualAppDisjuction = Restrictions.disjunction();
            virtualAppDisjuction.add(Restrictions.eq("idVirtualApp", vApp.getId()));
            ArrayList<NodeHB> nodesHb =
                (ArrayList<NodeHB>) session.createCriteria(NodeHB.class).add(virtualAppDisjuction)
                    .list();
            for (NodeHB node : nodesHb)
            {
                nodes.add((Node) node.toPojo());
            }
        }

        return nodes;

    }

    /**
     * This method recover the nodeHB list of a virtualAppliance IT's used to support third party
     * methods (It doesn't create a session)
     * 
     * @throws SchedulerException when something occurs
     */
    private Set<NodeHB> getNodesHBFromVirtualApp(Session session, VirtualAppliance vApp)
        throws SchedulerException
    {

        // Transform to HB object
        VirtualappHB vAppHB = (VirtualappHB) vApp.toPojoHB();

        // Maybe vApp has the nodes information
        Set<NodeHB> nodes = vAppHB.getNodesHB();

        // If not... recover the information
        if (nodes == null)
        {
            Disjunction virtualAppDisjuction = Restrictions.disjunction();
            virtualAppDisjuction.add(Restrictions.eq("virtualapp", vAppHB));
            nodes =
                (Set<NodeHB>) session.createCriteria("NodeVirtualImageHB")
                    .add(virtualAppDisjuction).list();
        }

        return nodes;

    }

    /**
     * Private helper to delete the virtual machines instantiated in the virtual machine
     * 
     * @param virtualAppliance the virtual appliance to delete the virtual machines
     * @param dataResult the data result for populating the errors
     */
    private void deleteVirtualMachines(VirtualAppliance virtualAppliance,
        DataResult<VirtualAppliance> dataResult)
    {

        Transaction transaction = null;
        try
        {
            Session session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Recovering Nodes
            ArrayList<Node> nodes = getNodesFromVirtualApp(session, virtualAppliance);

            for (Node node : nodes)
            {
                // Assure that the node is Virtual Image Type
                if (node.getNodeType().getId() == NodeType.VIRTUALIMAGE)
                {
                    // Convert to virtualImage node
                    NodeVirtualImage nodeVI = (NodeVirtualImage) node;
                    VirtualMachine virtualMachine = nodeVI.getVirtualMachine();
                    if (virtualMachine != null)
                    {
                        logger.debug("HD used: " + virtualMachine.getHd());
                        HyperVisor hypervisor = (HyperVisor) virtualMachine.getAssignedTo();
                        logger.debug("Restoring the physical machine resources");
                        HypervisorHB hypervisorHB =
                            (HypervisorHB) session.get(HypervisorHB.class, hypervisor.getId());
                        PhysicalmachineHB physicalMachineHB =
                            (PhysicalmachineHB) hypervisorHB.getPhysicalMachine();
                        PhysicalMachine physicalMachine =
                            (PhysicalMachine) physicalMachineHB.toPojo();
                        logger.debug("cpu used: " + virtualMachine.getCpu() + "ram used: "
                            + virtualMachine.getRam() + "hd used: " + virtualMachine.getHd());
                        scheduler.rollback(virtualMachine, physicalMachine);
                        // Deleting the virtual machine instance from DB
                        VirtualmachineHB vmachineHB =
                            (VirtualmachineHB) session.get(VirtualmachineHB.class, virtualMachine
                                .getId());
                        session.delete(vmachineHB);
                    }

                }
            }
            transaction.commit();
        }
        catch (HibernateException e)
        {
            if (transaction != null & transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManager, dataResult, "deleteVirtualMachines", e);
        }
        catch (SchedulerException e)
        {
            if (transaction != null & transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManager, dataResult, "deleteVirtualMachines", e);
        }
    }

    // Creates the virutal machine - helper method to startVirtualAppliance
    /**
     * This method creates the virtual machine. Is the helper method to startVirtualAppliance
     * 
     * @param virtualAppliance virtualApp where the virtual machines has to run
     * @param dataResult the result
     * @throws SchedulerException if there is a problem in the process
     */
    private void createVirtualMachines(VirtualAppliance virtualAppliance, DataResult dataResult)
        throws SchedulerException
    {

        Transaction transaction = null;

        try
        {
            Session session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            NodeVirtualImageHB nodeHBPojo;
            VirtualImage virtualImage;
            VirtualimageHB virtualImagePojo;

            // Recovering Nodes
            ArrayList<Node> nodes = getNodesFromVirtualApp(session, virtualAppliance);
            ArrayList<Node> nodesUpdated = new ArrayList<Node>();

            // Traverse all the nodes in "virtualAppliance"
            for (Node node : nodes)
            {
                if (node.getNodeType().getId() == NodeType.VIRTUALIMAGE)
                {
                    nodeHBPojo =
                        (NodeVirtualImageHB) session.get("NodeVirtualImageHB", node.getId());

                    // Convert to virtualImage node
                    NodeVirtualImage nodeVI = (NodeVirtualImage) node;

                    virtualImage = nodeVI.getVirtualImage();

                    virtualImagePojo =
                        (VirtualimageHB) session.get(VirtualimageHB.class, virtualImage.getId());

                    this.createNodeVirtualMachine(session, virtualImagePojo, nodeHBPojo);

                    node = (Node) nodeHBPojo.toPojo();

                    nodesUpdated.add(node);
                }

            }

            virtualAppliance.setNodes(nodesUpdated);

            VirtualappHB virtualappPojo =
                (VirtualappHB) session.get("VirtualappHB", virtualAppliance.getId());

            session.update("VirtualappExtendedHB", virtualappPojo);

        }
        catch (HibernateException e)
        {
            if (transaction != null & transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManager, dataResult, "createVirtualMachines", e);
        }

    }

    /**
     * Private helper to update the state in the Database
     * 
     * @param virtualappliance
     * @param newState the new state to update
     * @return a basic Result with the operation result
     */
    private DataResult<VirtualAppliance> updateStateInDB(VirtualAppliance virtualappliance,
        State newState, Transaction... args)
    {
        DataResult<VirtualAppliance> dataResult;
        dataResult = new DataResult<VirtualAppliance>();
        dataResult.setSuccess(true);
        Session session = null;
        Transaction transaction = null;
        try
        {
            session = HibernateUtil.getSession();
            transaction = (args.length > 0) ? args[0] : session.beginTransaction();

            Set<NodeHB> nodesPojo = null;
            VirtualappHB virtualAppPojo =
                (VirtualappHB) session.get("VirtualappExtendedHB", virtualappliance.getId());
            nodesPojo = virtualAppPojo.getNodesHB();

            for (NodeHB nodePojo : nodesPojo)
            {
                // Only for NodeVirtualImage Objects
                if (nodePojo.getNodeTypeHB().getIdNodeType() == NodeType.VIRTUALIMAGE)
                {
                    NodeVirtualImageHB nodeVI = (NodeVirtualImageHB) nodePojo;
                    StateHB newStatePojo = new StateHB();
                    newStatePojo.setIdState(newState.getId());
                    VirtualmachineHB virtualMachinePojo = nodeVI.getVirtualMachineHB();
                    if (virtualMachinePojo != null)
                    {
                        virtualMachinePojo.setState(newStatePojo);
                        session.update(virtualMachinePojo);
                    }
                }
            }
            StateHB newStatePojo = new StateHB();
            newStatePojo.setIdState(newState.getId());
            virtualAppPojo.setState(newStatePojo);
            session.update("VirtualappHB", virtualAppPojo);
            virtualappliance = (VirtualAppliance) virtualAppPojo.toPojo();
            if (args.length <= 0)
                transaction.commit();

            dataResult.setData(virtualappliance);
        }
        catch (HibernateException e)
        {
            if (transaction != null & transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManager, dataResult, "updateStateInDB", e);
        }
        return dataResult;
    }

    /**
     * Performs a "Shutdown" action in the Virtual Machine
     * 
     * @param virtualAppliance
     * @return a DataResult object, with a State object that represents the state "Powered Off"
     */
    protected DataResult<VirtualAppliance> shutdownVirtualAppliance(
        VirtualAppliance virtualAppliance)
    {
        DataResult<VirtualAppliance> dataResult = new DataResult<VirtualAppliance>();
        BasicResult basicResult = null;
        VirtualApplianceWS virtualApplianceWs;

        // Saving the state of the virtual appliance sent by the user
        State oldState = virtualAppliance.getState();

        // Checking the current state of the virtual appliance
        DataResult<VirtualAppliance> currentStateAndAllow;

        Integer virtualApplianceId = virtualAppliance.getId();

        try
        {
            currentStateAndAllow = checkVirtualApplianceState(virtualAppliance);
        }
        catch (Exception e)
        {
            // There was a problem checking the state of the virtual appliance. We can not
            // manipulate it
            this.errorManager.reportError(resourceManager, dataResult, "shutdownVirtualAppliance",
                e, virtualApplianceId);
            dataResult.setData(virtualAppliance); // dataResult.setData(oldState);
            return dataResult;
        }

        if (currentStateAndAllow.getSuccess())
        {
            try
            {
                // Calling the webservice
                virtualApplianceWs = new VirtualApplianceWS();
                basicResult = virtualApplianceWs.shutdownVirtualAppliance(virtualAppliance);

                if (basicResult.getSuccess())
                {
                    basicResult = virtualApplianceWs.deleteVirtualAppliance(virtualAppliance);
                    deleteVirtualMachines(virtualAppliance, dataResult);
                }
            }
            catch (Exception e)
            {
                // Leaving the virtual appliance with its original state
                virtualAppliance = updateStateInDB(virtualAppliance, oldState).getData();
                this.errorManager.reportError(resourceManager, dataResult,
                    "shutdownVirtualAppliance", e, virtualApplianceId);
                dataResult.setData(virtualAppliance);
                return dataResult;
            }

            dataResult.setMessage(basicResult.getMessage());
            dataResult.setSuccess(basicResult.getSuccess());
            if (basicResult.getSuccess())
            {
                // Everything went fine
                // Setting the new State of the Virtual Appliance
                State newState = new State();
                newState.setId(State.NOT_DEPLOYED);
                virtualAppliance = updateStateInDB(virtualAppliance, newState).getData();
                dataResult.setData(virtualAppliance);
            }
            else
            {
                // There was a problem changing the state of the virtual appliance
                // Leaving it with its original state
                virtualAppliance = updateStateInDB(virtualAppliance, oldState).getData();
                dataResult.setData(virtualAppliance);
            }

            return dataResult;
        }
        else
        {
            // The Virtual Appliance is being used by other user, or it is not up to date.
            // We inform the new state
            dataResult.setSuccess(true);
            dataResult.setMessage(VirtualApplianceCommand.resourceManager
                .getMessage("shutdownVirtualAppliance.modifiedByOther"));
            dataResult.setData(currentStateAndAllow.getData());

            return dataResult;
        }
    }

    /**
     * Checks if the state of a given virtual appliance is actually the last valid state in the Data
     * Base If it is the same, the state of the virtual appliance will be updated to
     * State.IN_PROGRESS, and a boolean will be returned to true, to indicate that the virtual
     * appliance can be manipulated Otherwise, the current state will be returned, and the boolean
     * will be set to false, indicating that the virtual appliance can not be manipulated
     * 
     * @param virtualAppliance The virtual appliance that will be checked
     * @return A DataResult object, containing a boolean that indicates if the virtual appliance can
     *         be manipulated and, in any case, it will contain the virtualAppliance with the
     *         current values in Data Base (this returned VirtualAppliance will also contain the
     *         node list!)
     * @throws Exception An Exception is thrown if there was a problem connecting to the Data base
     */
    private DataResult<VirtualAppliance> checkVirtualApplianceState(
        VirtualAppliance virtualAppliance) throws Exception
    {

        Session session = null;
        Transaction transaction = null;

        DataResult<VirtualAppliance> currentStateAndAllow = new DataResult<VirtualAppliance>();

        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Getting the current saved values for this Virtual Appliance
            VirtualappHB virtualAppHB =
                (VirtualappHB) session.get("VirtualappExtendedHB", virtualAppliance.getId());

            if (virtualAppliance.getState().getId() == virtualAppHB.getState().getIdState()
                && virtualAppHB.getState().getIdState() != State.IN_PROGRESS)
            {
                // The given virtual appliance is up to date, and is not in progress.
                // We set it now to IN_PROGRESS, and return that it is allowed to manipulate it
                StateHB newStateHB = new StateHB();
                newStateHB.setIdState(State.IN_PROGRESS);
                virtualAppHB.setState(newStateHB);

                session.update("VirtualappHB", virtualAppHB);

                // Generating the result
                currentStateAndAllow.setSuccess(true);
                currentStateAndAllow.setData((VirtualAppliance) virtualAppHB.toPojo());
            }
            else
            {
                // The given virtual appliance is not up to date, or the virtual appliance
                // is already in the state State.IN_PROGRESS. Manipulating it is not allowed

                // Generating the result
                currentStateAndAllow.setSuccess(false);
                currentStateAndAllow.setData((VirtualAppliance) virtualAppHB.toPojo());
            }

            transaction.commit();
        }

        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();
            throw e;
        }

        return currentStateAndAllow;
    }

    /**
     * Returns the a list with all Logs entries for a Virtual Appliance Useful to frequently update
     * the logs for a VirtualAppliance, without having to return the entire Virtual Appliance
     * 
     * @param session
     * @param virtualAppliance The VirtualAppliance which we want to return the list of logs
     * @return A DataResult object, containing an ArrayList<Log> with the list of logs for the
     *         virtualAppliance
     */
    protected DataResult<ArrayList<Log>> getVirtualApplianceUpdatedLogs(
        VirtualAppliance virtualAppliance)
    {
        Session session = null;
        Transaction transaction = null;
        DataResult<ArrayList<Log>> dataResult = new DataResult<ArrayList<Log>>();

        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Getting the VirtualAppliance
            VirtualappHB virtualappHB =
                (VirtualappHB) session.get("VirtualappHB", virtualAppliance.getId());
            VirtualAppliance virtualApplianceUpdated = (VirtualAppliance) virtualappHB.toPojo();

            // Building result
            dataResult.setData(virtualApplianceUpdated.getLogs());
            dataResult.setSuccess(true);
            dataResult.setMessage(VirtualApplianceCommand.resourceManager
                .getMessage("getVirtualApplianceUpdatedLogs.success"));

            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManager, dataResult,
                "getVirtualApplianceUpdatedLogs", e, virtualAppliance.getId());
        }

        return dataResult;
    }

    /**
     * Retrieves a VirtualAppliance, with the current values in DataBase. Since a client can have an
     * old version of a VirtualAppliance, this service is useful to get the updated state of a
     * Virtual Appliance
     * 
     * @param virtualAppliance The VirtualAppliance to check.
     * @return a DataResult<VirtualAppliance> object with the last updated values in DataBase. The
     *         returned VirtualAppliance will contain its list of noded
     */
    @SuppressWarnings("unchecked")
    protected DataResult<VirtualAppliance> checkVirtualAppliance(VirtualAppliance virtualAppliance)
    {
        DataResult<VirtualAppliance> dataResult = new DataResult<VirtualAppliance>();
        Session session = null;
        Transaction transaction = null;

        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Getting the given VirtualAppliance from the DataBase
            VirtualappHB updatedVirtualAppHB =
                (VirtualappHB) session.get("VirtualappExtendedHB", virtualAppliance.getId());

            // Generating the result
            dataResult.setSuccess(true);
            dataResult.setData((VirtualAppliance) updatedVirtualAppHB.toPojo());
            dataResult.setMessage(VirtualApplianceCommand.resourceManager
                .getMessage("checkVirtualAppliance.success"));

            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManager, dataResult,
                "checkVirtualAppliancesState", e);
        }

        return dataResult;
    }

    // /////////////////////////
    // VirtualDataCenter

    /**
     * Retrieves a list of VirtualDataCenter that belongs to the same Enterprise
     * 
     * @param enterprise The Enterprise of which the virtualdatacenter will be returned. If null, an
     *            empty Array will be returned
     * @return a DataResult object, containing an ArrayList<VirtualDataCenter>, with the
     *         VirtualDataCenter assigned to the enterprise
     */
    protected DataResult<ArrayList<VirtualDataCenter>> getVirtualDataCentersByEnterprise(
        Enterprise enterprise)
    {

        DataResult<ArrayList<VirtualDataCenter>> dataResult =
            new DataResult<ArrayList<VirtualDataCenter>>();

        Session session = null;
        Transaction transaction = null;

        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            ArrayList<VirtualDataCenter> virtualDataCenters = new ArrayList<VirtualDataCenter>();

            if (enterprise != null)
            {
                // Getting the EnterpriseHB
                EnterpriseHB enterpriseHB =
                    (EnterpriseHB) session.get(EnterpriseHB.class, enterprise.getId());

                // Getting the VirtualDataCenter list
                ArrayList<VirtualDataCenterHB> virtualDataCentersHB =
                    (ArrayList<VirtualDataCenterHB>) session.createCriteria(
                        VirtualDataCenterHB.class).add(
                        Restrictions.eq("enterpriseHB", enterpriseHB)).addOrder(Order.asc("name"))
                        .list();

                for (VirtualDataCenterHB virtualDataCenterHB : virtualDataCentersHB)
                {
                    virtualDataCenters.add((VirtualDataCenter) virtualDataCenterHB.toPojo());
                }
            }
            else
            {
                // If enterprise is null, an empty ArrayList will be returned
            }

            // Building result
            dataResult.setSuccess(true);
            dataResult.setData(virtualDataCenters);
            dataResult.setMessage(VirtualApplianceCommand.resourceManager
                .getMessage("getVirtualDataCenters.success"));

            transaction.commit();

        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManager, dataResult, "getVirtualDataCenters", e);
        }

        return dataResult;
    }

    /**
     * Creates a new VirtualDataCenter in the Data Base
     * 
     * @param userSession The UserSession with the user that called this method
     * @param virtualDataCenter The VirtualDataCenter that will be created in Data Base
     * @return a DataResult object containing the VirtualDataCenter that has been created
     */
    protected DataResult<VirtualDataCenter> createVirtualDataCenter(UserSession userSession,
        VirtualDataCenter virtualDataCenter)
    {
        DataResult<VirtualDataCenter> dataResult = new DataResult<VirtualDataCenter>();

        Session session = null;
        Transaction transaction = null;

        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Getting the user who has called this method
            UserHB userInfoHB =
                (UserHB) session.createCriteria(UserHB.class).add(
                    Restrictions.eq("user", userSession.getUser())).uniqueResult();

            // Building the VirtualDataCenter that will be created
            VirtualDataCenterHB virtualDataCenterHB =
                (VirtualDataCenterHB) virtualDataCenter.toPojoHB();
            virtualDataCenterHB.setUserHBByIdUserCreation(userInfoHB);
            virtualDataCenterHB.setCreationDate(new Date());

            session.save(virtualDataCenterHB);

            // Building the result
            VirtualDataCenter createdVirtualDataCenter =
                (VirtualDataCenter) virtualDataCenterHB.toPojo();
            dataResult.setSuccess(true);
            dataResult.setData(createdVirtualDataCenter);
            dataResult.setMessage(VirtualApplianceCommand.resourceManager
                .getMessage("createVirtualDataCenter.success"));

            transaction.commit();

        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager
                .reportError(resourceManager, dataResult, "createVirtualDataCenter", e);
        }

        return dataResult;
    }

    /**
     * Updates an existing VirtualDataCenter with new information
     * 
     * @param userSession The UserSession with the user that called this method
     * @param virtualDataCenter The VirtualDataCenter that will be updated
     * @return a BasicResult object, with the success of the edition
     */
    protected BasicResult editVirtualDataCenter(UserSession userSession,
        VirtualDataCenter virtualDataCenter)
    {
        BasicResult basicResult = new BasicResult();

        Session session = null;
        Transaction transaction = null;

        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Getting the user who has called this method
            UserHB userInfoHB =
                (UserHB) session.createCriteria(UserHB.class).add(
                    Restrictions.eq("user", userSession.getUser())).uniqueResult();

            // Getting the VirtualDataCenter that will be edited
            VirtualDataCenterHB virtualDataCenterHB =
                (VirtualDataCenterHB) session.get(VirtualDataCenterHB.class, virtualDataCenter
                    .getId());

            // Updating fields
            virtualDataCenterHB.setName(virtualDataCenter.getName());
            virtualDataCenterHB.setEnterpriseHB((EnterpriseHB) virtualDataCenter.getEnterprise()
                .toPojoHB());
            virtualDataCenterHB.setLastModificationDate(new Date());
            virtualDataCenterHB.setUserHBByIdUserLastModification(userInfoHB);

            session.update(virtualDataCenterHB);

            // Building result
            basicResult.setSuccess(true);
            basicResult.setMessage(VirtualApplianceCommand.resourceManager
                .getMessage("editVirtualDataCenter.success"));

        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManager, basicResult, "editVirtualDataCenter", e);
        }

        return basicResult;
    }

    /**
     * Deletes a VirtualDataCenter from the DataBase. A VirtualDataCenter can only be deleted if any
     * of its Virtual Appliances are powered on
     * 
     * @param virtualDataCenter The VirtualDataCenter to be deleted
     * @return A BasicResult object with the success of the deletion. BasicResult.success = false
     *         will be returned if the VirtualDataCenter has any assigned VirtualAppliance powered
     *         on
     */
    protected BasicResult deleteVirtualDataCenter(VirtualDataCenter virtualDataCenter)
    {
        BasicResult basicResult = new BasicResult();

        Session session = null;
        Transaction transaction = null;

        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Getting the VirtualDataCenterHB to be deleted
            VirtualDataCenterHB virtualDataCenterHB =
                (VirtualDataCenterHB) session.get(VirtualDataCenterHB.class, virtualDataCenter
                    .getId());

            // Checking if we can delete this VirtualDataCenterHB
            StateHB poweredOnStateHB = new StateHB();
            poweredOnStateHB.setIdState(State.RUNNING);
            Integer numVirtualAppPoweredOn =
                (Integer) session.createCriteria("VirtualappHB").add(
                    Restrictions.eq("virtualDataCenterHB", virtualDataCenterHB)).add(
                    Restrictions.eq("state", poweredOnStateHB)).setProjection(
                    Projections.rowCount()).uniqueResult();

            if (numVirtualAppPoweredOn == 0)
            {
                // We can safely delete this VirtualDataCenter
                session.delete(virtualDataCenterHB);

                basicResult.setSuccess(true);
                basicResult.setMessage(VirtualApplianceCommand.resourceManager
                    .getMessage("deleteVirtualDataCenter.success"));
            }
            else
            {
                // We can not delete this VirtualDataCenter until all its VirtualAppliance are
                // powered off
                basicResult.setSuccess(false);
                basicResult.setMessage(VirtualApplianceCommand.resourceManager
                    .getMessage("deleteVirtualDataCenter.unableToDelete"));
            }

            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManager, basicResult, "deleteVirtualDataCenter",
                e);
        }

        return basicResult;
    }

}
