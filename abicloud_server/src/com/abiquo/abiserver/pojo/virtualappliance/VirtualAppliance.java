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
package com.abiquo.abiserver.pojo.virtualappliance;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.abiquo.abiserver.business.hibernate.pojohb.IPojoHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.StateHB;
import com.abiquo.abiserver.business.hibernate.pojohb.user.EnterpriseHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualappliance.LogHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualappliance.NodeHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualappliance.VirtualDataCenterHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualappliance.VirtualappHB;
import com.abiquo.abiserver.pojo.IPojo;
import com.abiquo.abiserver.pojo.infrastructure.State;
import com.abiquo.abiserver.pojo.user.Enterprise;

/**
 * This class represents a Virtual Appliance
 * 
 * @author Oliver
 */

public class VirtualAppliance implements IPojo
{

    /* ------------- Public atributes ------------- */
    private int id;

    private String name;

    private Boolean isPublic;

    private State state;

    private Boolean highDisponibility;

    // Array containing a list of nodes
    // This array may not be always filled, depending on how the Virtual Appliance
    // is retrieved with Hibernate
    private ArrayList<Node> nodes;

    // String with an XML document that contains the relations between nodes
    private String nodeConnections;

    // Array containing a Log list
    private ArrayList<Log> logs;

    /**
     * false - no errors true - errors
     */
    private Boolean error;

    /**
     * The virtualDataCenter identification.
     */
    private VirtualDataCenter virtualDataCenter;

    /**
     * The Enterprise to which this Virtual Appliance belongs it may be null, if this virtual
     * appliance is not assigned to any Enterprise
     */
    private Enterprise enterprise;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Boolean getIsPublic()
    {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic)
    {
        this.isPublic = isPublic;
    }

    public State getState()
    {
        return state;
    }

    public void setState(State state)
    {
        this.state = state;
    }

    public Boolean getHighDisponibility()
    {
        return highDisponibility;
    }

    public void setHighDisponibility(Boolean highDisponibility)
    {
        this.highDisponibility = highDisponibility;
    }

    public ArrayList<Node> getNodes()
    {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes)
    {
        this.nodes = nodes;
    }

    public String getNodeConnections()
    {
        return nodeConnections;
    }

    public void setNodeConnections(String nodeConnections)
    {
        this.nodeConnections = nodeConnections;
    }

    public Boolean getError()
    {
        return error;
    }

    public void setError(Boolean error)
    {
        this.error = error;
    }

    public VirtualDataCenter getVirtualDataCenter()
    {
        return virtualDataCenter;
    }

    public void setVirtualDataCenter(VirtualDataCenter virtualDataCenter)
    {
        this.virtualDataCenter = virtualDataCenter;
    }

    public Enterprise getEnterprise()
    {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise)
    {
        this.enterprise = enterprise;
    }

    public ArrayList<Log> getLogs()
    {
        return logs;
    }

    public void setLogs(ArrayList<Log> logs)
    {
        this.logs = logs;
    }

    /**
     * This method transform the pojo object to hibernate pojo object.
     */
    public IPojoHB toPojoHB()
    {
        VirtualappHB virtualappHB = new VirtualappHB();
        virtualappHB.setIdVirtualApp(this.id);
        virtualappHB.setName(this.name);
        virtualappHB.setHighDisponibility(this.highDisponibility ? 1 : 0);
        virtualappHB.setPublic_(this.isPublic ? 1 : 0);
        virtualappHB.setState((StateHB) this.state.toPojoHB());
        virtualappHB.setError(this.error ? 1 : 0);
        virtualappHB
            .setVirtualDataCenterHB((VirtualDataCenterHB) this.virtualDataCenter.toPojoHB());
        virtualappHB.setNodeConnections(this.nodeConnections);

        if (this.enterprise != null)
            virtualappHB.setEnterpriseHB((EnterpriseHB) this.enterprise.toPojoHB());
        else
            virtualappHB.setEnterpriseHB(null);

        if (this.logs != null)
        {
            Set<LogHB> logsHB = new HashSet<LogHB>(0);
            for (Log log : this.logs)
            {
                logsHB.add((LogHB) log.toPojoHB());
            }

            virtualappHB.setLogsHB(logsHB);
        }
        else
            virtualappHB.setLogsHB(null);

        if (this.nodes != null)
        {
            Set<NodeHB> nodesHB = new HashSet<NodeHB>(0);
            for (Node node : this.nodes)
            {
                nodesHB.add((NodeHB) node.toPojoHB());
            }

            virtualappHB.setNodesHB(nodesHB);
        }
        else
            virtualappHB.setNodesHB(null);

        return virtualappHB;

    }

}
