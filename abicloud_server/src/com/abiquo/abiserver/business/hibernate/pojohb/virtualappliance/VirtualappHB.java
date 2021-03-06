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
package com.abiquo.abiserver.business.hibernate.pojohb.virtualappliance;

// Generated 16-oct-2008 16:52:14 by Hibernate Tools 3.2.1.GA

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import com.abiquo.abiserver.business.hibernate.pojohb.IPojoHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.StateHB;
import com.abiquo.abiserver.business.hibernate.pojohb.user.EnterpriseHB;
import com.abiquo.abiserver.business.hibernate.pojohb.user.UserHB;
import com.abiquo.abiserver.pojo.IPojo;
import com.abiquo.abiserver.pojo.infrastructure.State;
import com.abiquo.abiserver.pojo.user.Enterprise;
import com.abiquo.abiserver.pojo.virtualappliance.Log;
import com.abiquo.abiserver.pojo.virtualappliance.Node;
import com.abiquo.abiserver.pojo.virtualappliance.VirtualAppliance;
import com.abiquo.abiserver.pojo.virtualappliance.VirtualDataCenter;

/**
 * Virtualapp generated by hbm2java
 */
public class VirtualappHB implements java.io.Serializable, IPojoHB {

	private static final long serialVersionUID = 4088733370483687503L;
	
	private Integer idVirtualApp;
	private UserHB userHBByIdUserLastModification;
	private StateHB state;
	private UserHB userHBByIdUserCreation;
	private String name;
	private int public_;
	private Set<NodeHB> nodesHB;
	private String nodeConnections;
	private int highDisponibility;
	private Date creationDate;
	private Date lastModificationDate;
	
	
	
	/**
	 * 0 - No errors
	 * 1 - There are errors
	 */
	private Integer error;
	
	/**
	 * The virtualDataCenter identification.
	 */
	private VirtualDataCenterHB virtualDataCenterHB;

	/**
	 * The Enterprise to which this Virtual Appliance belongs
	 */
	private EnterpriseHB enterpriseHB;
	
	/**
	 * LogHB list, with the log entries for this Virtual Appliance
	 * @return
	 */
	private Set<LogHB> logsHB;
	

	public Integer getIdVirtualApp() {
		return this.idVirtualApp;
	}

	public void setIdVirtualApp(Integer idVirtualApp) {
		this.idVirtualApp = idVirtualApp;
	}

	public UserHB getUserHBByIdUserLastModification() {
		return this.userHBByIdUserLastModification;
	}

	public void setUserHBByIdUserLastModification(
			UserHB userHBByIdUserLastModification) {
		this.userHBByIdUserLastModification = userHBByIdUserLastModification;
	}

	public StateHB getState() {
		return this.state;
	}

	public void setState(StateHB state) {
		this.state = state;
	}

	public UserHB getUserHBByIdUserCreation() {
		return this.userHBByIdUserCreation;
	}

	public void setUserHBByIdUserCreation(UserHB userHBByIdUserCreation) {
		this.userHBByIdUserCreation = userHBByIdUserCreation;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPublic_() {
		return this.public_;
	}

	public void setPublic_(int public_) {
		this.public_ = public_;
	}

	public int getHighDisponibility() {
		return this.highDisponibility;
	}

	public void setHighDisponibility(int highDisponibility) {
		this.highDisponibility = highDisponibility;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastModificationDate() {
		return this.lastModificationDate;
	}

	public void setLastModificationDate(Date lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}

	public Integer getError() {
		return error;
	}

	public void setError(Integer error) {
		this.error = error;
	}

	public VirtualDataCenterHB getVirtualDataCenterHB() {
		return virtualDataCenterHB;
	}

	public void setVirtualDataCenterHB(VirtualDataCenterHB virtualDataCenterHB) {
		this.virtualDataCenterHB = virtualDataCenterHB;
	}
	
	public EnterpriseHB getEnterpriseHB() {
		return enterpriseHB;
	}

	public void setEnterpriseHB(EnterpriseHB enterpriseHB) {
		this.enterpriseHB = enterpriseHB;
	}

	public Set<NodeHB> getNodesHB() {
		return nodesHB;
	}

	public void setNodesHB(Set<NodeHB> nodesHB) {
		this.nodesHB = nodesHB;
	}

	public String getNodeConnections() {
		return nodeConnections;
	}

	public void setNodeConnections(String nodeConnections) {
		this.nodeConnections = nodeConnections;
	}

	public Set<LogHB> getLogsHB() {
		return logsHB;
	}

	public void setLogsHB(Set<LogHB> logsHB) {
		this.logsHB = logsHB;
	}

	/**
	 * This method transform the hibernate pojo to normal pojo object
	 */
	public IPojo toPojo()
	{
		VirtualAppliance virtualAppliance = new VirtualAppliance();
		virtualAppliance.setHighDisponibility(this.highDisponibility != 0);
		virtualAppliance.setId(this.idVirtualApp);
		virtualAppliance.setIsPublic(this.public_!= 0);
		virtualAppliance.setName(this.name);
		virtualAppliance.setState((State)this.state.toPojo());
		virtualAppliance.setError(this.error != 0);
		virtualAppliance.setVirtualDataCenter((VirtualDataCenter)this.virtualDataCenterHB.toPojo());
		virtualAppliance.setNodeConnections(this.nodeConnections);
		
		if(this.enterpriseHB != null)
			virtualAppliance.setEnterprise((Enterprise) this.enterpriseHB.toPojo());
		else
			virtualAppliance.setEnterprise(null);
		
		if(this.logsHB != null)
		{
			ArrayList<Log> logs = new ArrayList<Log>();
			for(LogHB logHB : this.logsHB)
			{
				logs.add((Log) logHB.toPojo());
			}
			
			virtualAppliance.setLogs(logs);
		}
		else
			virtualAppliance.setLogs(null);
		
		
		if(this.nodesHB != null)
		{
			ArrayList<Node> nodes = new ArrayList<Node>();
			for(NodeHB nodeHB : this.nodesHB)
			{
				nodes.add((Node) nodeHB.toPojo());
			}
			
			virtualAppliance.setNodes(nodes);
		}
		else
			virtualAppliance.setNodes(null);
	
		return  virtualAppliance;
	}

}
