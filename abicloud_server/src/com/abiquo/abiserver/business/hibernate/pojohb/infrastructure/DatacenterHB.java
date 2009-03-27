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
package com.abiquo.abiserver.business.hibernate.pojohb.infrastructure;

// Generated 16-oct-2008 16:52:14 by Hibernate Tools 3.2.1.GA

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.abiquo.abiserver.business.hibernate.pojohb.IPojoHB;
import com.abiquo.abiserver.business.hibernate.pojohb.user.UserHB;
import com.abiquo.abiserver.pojo.IPojo;
import com.abiquo.abiserver.pojo.infrastructure.DataCenter;


public class DatacenterHB implements java.io.Serializable, IPojoHB {


	private static final long serialVersionUID = 5404783524189416182L;
	private Integer idDataCenter;
	private UserHB userHBByIdUserLastModification;
	private UserHB userHBByIdUserCreation;
	private String name;
	private String situation;
	private Date creationDate;
	private Date lastModificationDate;
	private Set<RackHB> racks = new HashSet<RackHB>(0);

	public DatacenterHB() {
	}

	public DatacenterHB(UserHB userHBByIdUserCreation, String name, Date creationDate) {
		this.userHBByIdUserCreation = userHBByIdUserCreation;
		this.name = name;
		this.creationDate = creationDate;
	}

	public DatacenterHB(UserHB userHBByIdUserLastModification,
			UserHB userHBByIdUserCreation, String name, String situation,
			Date creationDate, Date lastModificationDate, Set<RackHB> racks) {
		this.userHBByIdUserLastModification = userHBByIdUserLastModification;
		this.userHBByIdUserCreation = userHBByIdUserCreation;
		this.name = name;
		this.situation = situation;
		this.creationDate = creationDate;
		this.lastModificationDate = lastModificationDate;
		this.racks = racks;
	}

	public Integer getIdDataCenter() {
		return this.idDataCenter;
	}

	public void setIdDataCenter(Integer idDataCenter) {
		this.idDataCenter = idDataCenter;
	}

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

	public String getSituation() {
		return this.situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
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

	public Set<RackHB> getRacks() {
		return this.racks;
	}

	public void setRacks(Set<RackHB> racks) {
		this.racks = racks;
	}

	public IPojo toPojo()
	{
		DataCenter dataCenter = new DataCenter();
		dataCenter.setId(this.idDataCenter);
		dataCenter.setName(this.name);
		dataCenter.setSituation(this.situation);
		return dataCenter;
	}

}
