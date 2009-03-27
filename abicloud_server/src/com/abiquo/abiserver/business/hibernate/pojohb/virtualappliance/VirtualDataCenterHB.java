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
 * Consell de Cent 296, Principal 2º, 08007 Barcelona, Spain.
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

import java.util.Date;

import com.abiquo.abiserver.business.hibernate.pojohb.IPojoHB;
import com.abiquo.abiserver.business.hibernate.pojohb.user.EnterpriseHB;
import com.abiquo.abiserver.business.hibernate.pojohb.user.UserHB;
import com.abiquo.abiserver.pojo.IPojo;
import com.abiquo.abiserver.pojo.user.Enterprise;
import com.abiquo.abiserver.pojo.virtualappliance.VirtualDataCenter;

/**
 * Virtual Data Center hibernate object.
 * In abiCloud, an enterprise can have any, one or many
 * virtual data centers
 * 
 * @author xfernandez
 *
 */
public class VirtualDataCenterHB implements java.io.Serializable, IPojoHB {


	/**
	 * serial  version UID object.
	 */
	private static final long serialVersionUID = 4033491980148971926L;
	
	/**
	 * identification of virtual Data Center.
	 */
	private Integer idVirtualDataCenter;
	
	/**
	 * User modifier.
	 */
	private UserHB userHBByIdUserLastModification;
	
	/**
	 * User creator.
	 */
	private UserHB userHBByIdUserCreation;
	
	/**
	 * Virtual data center name.
	 */
	private String name;
	
	/**
	 * The creation date.
	 */
	private Date creationDate;
	
	/**
	 *  The modification date.
	 */
	private Date lastModificationDate;
	
	/**
	 * The enterprise to which the VirtualDataCenter belongs to
	 */
	private EnterpriseHB enterpriseHB;
	

	public UserHB getUserHBByIdUserLastModification() {
		return this.userHBByIdUserLastModification;
	}

	public void setUserHBByIdUserLastModification(
			UserHB userHBByIdUserLastModification) {
		this.userHBByIdUserLastModification = userHBByIdUserLastModification;
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

	public Integer getIdVirtualDataCenter() {
		return idVirtualDataCenter;
	}

	public void setIdVirtualDataCenter(Integer idVirtualDataCenter) {
		this.idVirtualDataCenter = idVirtualDataCenter;
	}

	public EnterpriseHB getEnterpriseHB() {
		return enterpriseHB;
	}

	public void setEnterpriseHB(EnterpriseHB enterpriseHB) {
		this.enterpriseHB = enterpriseHB;
	}

	/**
	 * Method to create the generic pojo object.
	 */
	public IPojo toPojo()
	{
		VirtualDataCenter virtualDataCenter = new VirtualDataCenter();
		virtualDataCenter.setId(this.idVirtualDataCenter);
		virtualDataCenter.setName(this.name);
		virtualDataCenter.setEnterprise((Enterprise) this.enterpriseHB.toPojo());
		
		return virtualDataCenter;
	}

}
