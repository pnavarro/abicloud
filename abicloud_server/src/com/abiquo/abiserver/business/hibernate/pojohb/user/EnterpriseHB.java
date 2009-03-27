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
package com.abiquo.abiserver.business.hibernate.pojohb.user;

import java.util.Date;
import com.abiquo.abiserver.business.hibernate.pojohb.IPojoHB;
import com.abiquo.abiserver.pojo.IPojo;
import com.abiquo.abiserver.pojo.user.Enterprise;

/**
 * This object is the hibernate object
 * of the enterprise.
 * 
 * @author xfernandez
 *
 */
public class EnterpriseHB implements java.io.Serializable, IPojoHB{

	/**
	 * The generated serial Version UID.
	 */
	private static final long serialVersionUID = -4186633712801242561L;
	
	/**
	 * The Enterprise identification.
	 */
	private Integer idEnterprise;
	
	/**
	 * The enterprise name
	 */
	private String  name;
	
	/**
	 * Flag indicating if this enterprise has been deleted
	 */
	private Integer deleted;
	
	private UserHB userHBByIdUserCreation;
	private UserHB userHBByIdUserLastModification;
	
	private Date creationDate;
	private Date lastModificationDate;

	public Integer getIdEnterprise() {
		return idEnterprise;
	}

	public void setIdEnterprise(Integer idEnterprise) {
		this.idEnterprise = idEnterprise;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public UserHB getUserHBByIdUserCreation() {
		return userHBByIdUserCreation;
	}

	public void setUserHBByIdUserCreation(UserHB userHBByIdUserCreation) {
		this.userHBByIdUserCreation = userHBByIdUserCreation;
	}

	public UserHB getUserHBByIdUserLastModification() {
		return userHBByIdUserLastModification;
	}

	public void setUserHBByIdUserLastModification(
			UserHB userHBByIdUserLastModification) {
		this.userHBByIdUserLastModification = userHBByIdUserLastModification;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastModificationDate() {
		return lastModificationDate;
	}

	public void setLastModificationDate(Date lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}

	
	/**
	 * This method create a generic enterprise
	 * pojo object.
	 */
	public IPojo toPojo() {
		Enterprise enterprise = new Enterprise();
		
		enterprise.setId(this.idEnterprise);
		enterprise.setName(this.name);
		enterprise.setDeleted(this.deleted == 1 ? true:false);
		
		return enterprise;
	}
}
