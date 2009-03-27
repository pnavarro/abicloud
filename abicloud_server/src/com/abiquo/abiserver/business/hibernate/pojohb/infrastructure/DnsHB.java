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

import com.abiquo.abiserver.business.hibernate.pojohb.IPojoHB;
import com.abiquo.abiserver.business.hibernate.pojohb.user.UserHB;
import com.abiquo.abiserver.pojo.IPojo;
import com.abiquo.abiserver.pojo.infrastructure.DNS;


public class DnsHB implements java.io.Serializable, IPojoHB {

	
	private static final long serialVersionUID = 6625139725464528619L;
	
	private DnsIdHB id;
	private UserHB userHBByIdUserLastModification;
	private UserHB userHBByIdUserCreation;
	private String dns;
	private Date creationDate;
	private Date lastModificationDate;

	public DnsHB()
	{
		
	}
	
	public DnsHB(DnsIdHB id, UserHB userHBByIdUserLastModification,
			UserHB userHBByIdUserCreation, String dns,
			Date creationDate, Date lastModificationDate) {
		this.id = id;
		this.userHBByIdUserLastModification = userHBByIdUserLastModification;
		this.userHBByIdUserCreation = userHBByIdUserCreation;
		this.dns = dns;
		this.creationDate = creationDate;
		this.lastModificationDate = lastModificationDate;
	}

	public DnsIdHB getId() {
		return this.id;
	}

	public void setId(DnsIdHB id) {
		this.id = id;
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

	public String getDns() {
		return this.dns;
	}

	public void setDns(String dns) {
		this.dns = dns;
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

	public IPojo toPojo()
	{
		DNS dns = new DNS();
		dns.setDns(this.dns);
		dns.setId(this.id.getIdDns());
		return dns;
	}

}
