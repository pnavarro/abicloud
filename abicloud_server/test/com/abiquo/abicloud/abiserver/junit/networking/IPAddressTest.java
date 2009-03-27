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
package com.abiquo.abicloud.abiserver.junit.networking;

import junit.framework.TestCase;

import org.junit.Test;

import com.abiquo.networking.IPAddress;

/**
 * @author abiquo
 */
public class IPAddressTest extends TestCase
{

    /**
     * Test method for {@link com.abiquo.networking.IPAddress#newIPAddress(java.lang.String)}.
     */
    @Test
    public void testNewIPAddress()
    {
        // Invalid formats
        assertTrue(IPAddress.newIPAddress(null).isInvalid());
        assertTrue(IPAddress.newIPAddress("").isInvalid());
        assertTrue(IPAddress.newIPAddress("1.2.3a").isInvalid());
        assertTrue(IPAddress.newIPAddress(".2..3").isInvalid());
        assertTrue(IPAddress.newIPAddress("260.1.3.3").isInvalid());
        assertTrue(IPAddress.newIPAddress("34.12.179.2.4").isInvalid());
        assertTrue(IPAddress.newIPAddress("23.178. .21").isInvalid());

        // Valid formats
        assertTrue(!IPAddress.newIPAddress("23.43.255.0").isInvalid());
        assertTrue(!IPAddress.newIPAddress("34.12.98.12").isInvalid());
        assertTrue(!IPAddress.newIPAddress("255.0.0.255").isInvalid());
        assertTrue(!IPAddress.newIPAddress("0.0.0.0").isInvalid());

    }

    /**
     * Test method for {@link com.abiquo.networking.IPAddress#nextIPAddress()}.
     */
    @Test
    public void testNextIPAddress()
    {
        assertTrue(IPAddress.newIPAddress("0.0.0.0").nextIPAddress().toString().equalsIgnoreCase(
            "0.0.0.1"));
        assertTrue(IPAddress.newIPAddress("0.0.1.0").nextIPAddress().toString().equalsIgnoreCase(
            "0.0.1.1"));
        assertTrue(IPAddress.newIPAddress("22.33.43.55").nextIPAddress().toString()
            .equalsIgnoreCase("22.33.43.56"));
        assertTrue(IPAddress.newIPAddress("0.0.1.255").nextIPAddress().toString().equalsIgnoreCase(
            "0.0.2.0"));
        assertTrue(IPAddress.newIPAddress("0.0.255.255").nextIPAddress().toString()
            .equalsIgnoreCase("0.1.0.0"));
        assertTrue(IPAddress.newIPAddress("0.255.255.255").nextIPAddress().toString()
            .equalsIgnoreCase("1.0.0.0"));
        assertTrue(IPAddress.newIPAddress("0.255.0.255").nextIPAddress().toString()
            .equalsIgnoreCase("0.255.1.0"));
        assertTrue(IPAddress.newIPAddress("255.255.255.255").nextIPAddress().toString()
            .equalsIgnoreCase("0.0.0.0"));
        assertTrue(IPAddress.newIPAddress("0.255.0.abc").nextIPAddress().toString()
            .equalsIgnoreCase(""));
    }

    /**
     * Test method for {@link com.abiquo.networking.IPAddress#previousIPAddress()}.
     */
    @Test
    public void testPreviousIPAddress()
    {
        assertTrue(IPAddress.newIPAddress("0.0.0.1").previousIPAddress().toString()
            .equalsIgnoreCase("0.0.0.0"));
        assertTrue(IPAddress.newIPAddress("0.0.1.1").previousIPAddress().toString()
            .equalsIgnoreCase("0.0.1.0"));
        assertTrue(IPAddress.newIPAddress("22.33.43.56").previousIPAddress().toString()
            .equalsIgnoreCase("22.33.43.55"));
        assertTrue(IPAddress.newIPAddress("0.0.2.0").previousIPAddress().toString()
            .equalsIgnoreCase("0.0.1.255"));
        assertTrue(IPAddress.newIPAddress("0.1.0.0").previousIPAddress().toString()
            .equalsIgnoreCase("0.0.255.255"));
        assertTrue(IPAddress.newIPAddress("1.0.0.0").previousIPAddress().toString()
            .equalsIgnoreCase("0.255.255.255"));
        assertTrue(IPAddress.newIPAddress("0.255.1.0").previousIPAddress().toString()
            .equalsIgnoreCase("0.255.0.255"));
        assertTrue(IPAddress.newIPAddress("0.0.0.0").previousIPAddress().toString()
            .equalsIgnoreCase("255.255.255.255"));
        assertTrue(IPAddress.newIPAddress("0.255.0.abc").previousIPAddress().toString()
            .equalsIgnoreCase(""));
    }

    /**
     * Test method for {@link com.abiquo.networking.IPAddress#previousIPAddress()}.
     */
    @Test
    public void testLastIPAddressWithNumNodes()
    {
        IPAddress beginIP = IPAddress.newIPAddress("23.23.23.23");
        assertTrue(beginIP.lastIPAddressWithNumNodes(20).toString().equalsIgnoreCase("23.23.23.42"));
        assertTrue(beginIP.lastIPAddressWithNumNodes(1293879).toString().equalsIgnoreCase(
            "23.42.213.77"));
        assertTrue(beginIP.lastIPAddressWithNumNodes(1).toString().equalsIgnoreCase("23.23.23.23"));
        assertTrue(beginIP.lastIPAddressWithNumNodes(871287).toString().equalsIgnoreCase(
            "23.36.98.141"));
        assertTrue(beginIP.lastIPAddressWithNumNodes(4294967).toString().equalsIgnoreCase(
            "23.88.160.77"));
        assertNull(beginIP.lastIPAddressWithNumNodes(0));
        assertTrue(beginIP.lastIPAddressWithNumNodes(1).toString().equalsIgnoreCase("23.23.23.23"));
        assertTrue(beginIP.lastIPAddressWithNumNodes(2).toString().equalsIgnoreCase("23.23.23.24"));
        assertTrue(beginIP.lastIPAddressWithNumNodes(233).toString().equalsIgnoreCase(
            "23.23.23.255"));
        assertTrue(beginIP.lastIPAddressWithNumNodes(12343).toString().equalsIgnoreCase(
            "23.23.71.77"));
        assertTrue(beginIP.lastIPAddressWithNumNodes(78).toString()
            .equalsIgnoreCase("23.23.23.100"));
        assertTrue(beginIP.lastIPAddressWithNumNodes(123).toString().equalsIgnoreCase(
            "23.23.23.145"));
        assertTrue(beginIP.lastIPAddressWithNumNodes(16777216).toString().equalsIgnoreCase(
            "24.23.23.22"));
        assertTrue(beginIP.lastIPAddressWithNumNodes(65536).toString().equalsIgnoreCase(
            "23.24.23.22"));
        assertTrue(beginIP.lastIPAddressWithNumNodes(256).toString()
            .equalsIgnoreCase("23.23.24.22"));
        assertTrue(beginIP.lastIPAddressWithNumNodes(34).toString().equalsIgnoreCase("23.23.23.56"));

        beginIP = IPAddress.newIPAddress("10.0.0.0");
        assertTrue(beginIP.lastIPAddressWithNumNodes(256).toString().equalsIgnoreCase("10.0.0.255"));
    }

}
