/**
 * 
 */
package com.abiquo.abiserver.business.hibernate.pojohb.infrastructure;

import com.abiquo.abiserver.business.hibernate.pojohb.IPojoHB;
import com.abiquo.abiserver.pojo.IPojo;
import com.abiquo.abiserver.pojo.infrastructure.HyperVisorType;

/**
 * @author slizardo
 * 
 */
public class HypervisorTypeHB implements IPojoHB
{
	private Integer id;
	private String name;
	private int defaultPort;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
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
	
	
	
	public int getDefaultPort() {
		return defaultPort;
	}

	public void setDefaultPort(int defaultPort) {
		this.defaultPort = defaultPort;
	}

	public IPojo toPojo()
	{
		HyperVisorType hyperVisorType = new HyperVisorType();
		hyperVisorType.setId(this.id);
		hyperVisorType.setName(this.name);
		hyperVisorType.setDefaultPort(this.defaultPort);
		
		return hyperVisorType;
	}
}
