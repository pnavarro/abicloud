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
package com.abiquo.networking;

import java.util.StringTokenizer;

/**
 * This class represents a IPv4 Address and its features. Checks if has a correct format and
 * computes the next and the previous IPAddress. After build a new IPAddress object, its recommended
 * to check method isInvalid().
 * 
 * @author abiquo
 */
public class IPAddress
{
    /**
     * Object reference where we store the IP.
     */
    String ip;

    /**
     * IP Address constructor
     * 
     * @param ipAddress
     */
    private IPAddress(String ipAddress)
    {
        ip = ipAddress;
    }

    /**
     * Checks the correct format of the ipAddress and returns null if is not correct.
     * 
     * @param ipAddress proposed ipAddress
     * @return IPAddress reference
     */
    public static IPAddress newIPAddress(String ipAddress)
    {
        IPAddress ipAddressReference = null;
        boolean correctIP = true;

        // we make sure ipAddress is not null
        if (ipAddress != null)
        {
            // We make sure ipAddress is not null
            if (!ipAddress.isEmpty())
            {
                StringTokenizer token = new StringTokenizer(ipAddress, ".");
                String temporalToken;

                // It should have four iterations.
                for (int i = 0; i < 4; i++)
                {
                    // Has to have exactly 4 tokens!
                    if (token.hasMoreTokens())
                    {
                        temporalToken = token.nextToken();

                        // make sure is a number
                        try
                        {
                            Integer integerIP = Integer.parseInt(temporalToken);
                            // Number should be between 0 and 255
                            if (!(integerIP >= 0 && integerIP <= 255))
                            {
                                correctIP = false;
                            }
                        }
                        catch (NumberFormatException e)
                        {
                            correctIP = false;
                        }

                    }
                    else
                    {
                        correctIP = false;
                    }
                }

                // No more tokens! Only 4!
                if (token.hasMoreElements())
                {
                    correctIP = false;
                }
            }
            else
            {
                correctIP = false;
            }
        }
        else
        {
            correctIP = false;
        }

        // finally we build the code.
        if (correctIP)
        {
            ipAddressReference = new IPAddress(ipAddress);
        }
        else
        {
            ipAddressReference = new IPAddress("");
        }
        return ipAddressReference;
    }

    /**
     * Calculates the next IP address. Used for look for the next available IP address in DB.
     * 
     * @return an IPAddress object with the next one.
     */
    public IPAddress nextIPAddress()
    {
        if (!ip.isEmpty())
        {
            StringTokenizer tokenizer = new StringTokenizer(ip, ".");

            // Thanks to the static method we can be sure we have 4 tokens
            // and they live between 0 and 256
            int tokenOne = Integer.parseInt(tokenizer.nextToken());
            int tokenTwo = Integer.parseInt(tokenizer.nextToken());
            int tokenThree = Integer.parseInt(tokenizer.nextToken());
            int tokenFour = Integer.parseInt(tokenizer.nextToken());

            tokenFour = (tokenFour + 1) % 256;
            if (tokenFour == 0)
            {
                tokenThree = (tokenThree + 1) % 256;
                if (tokenThree == 0)
                {
                    tokenTwo = (tokenTwo + 1) % 256;
                    if (tokenTwo == 0)
                    {
                        tokenOne = (tokenOne + 1) % 256;
                    }
                }
            }
            return IPAddress.newIPAddress(tokenOne + "." + tokenTwo + "." + tokenThree + "."
                + tokenFour);
        }
        else
        {
            return IPAddress.newIPAddress(null);
        }
    }

    /**
     * Given a IPAddress, calculates its previous IPAddress.
     * 
     * @return previous IPAddress.
     */
    public IPAddress previousIPAddress()
    {
        if (!ip.isEmpty())
        {
            StringTokenizer tokenizer = new StringTokenizer(ip, ".");

            // Thanks to the static method we can be sure we have 4 tokens
            // and they live between 0 and 256
            int tokenOne = Integer.parseInt(tokenizer.nextToken());
            int tokenTwo = Integer.parseInt(tokenizer.nextToken());
            int tokenThree = Integer.parseInt(tokenizer.nextToken());
            int tokenFour = Integer.parseInt(tokenizer.nextToken());

            // Note: Negative numbers doesn't compute the operator %, so, instead of subtract -1
            // we decide to sum +255
            tokenFour = (tokenFour + 255) % 256;
            if (tokenFour == 255)
            {
                tokenThree = (tokenThree + 255) % 256;
                if (tokenThree == 255)
                {
                    tokenTwo = (tokenTwo + 255) % 256;
                    if (tokenTwo == 255)
                    {
                        tokenOne = (tokenOne + 255) % 256;
                    }
                }
            }
            return IPAddress.newIPAddress(tokenOne + "." + tokenTwo + "." + tokenThree + "."
                + tokenFour);
        }
        else
        {
            return IPAddress.newIPAddress(null);
        }
    }

    public IPAddress lastIPAddressWithNumNodes(Integer numberOfNodes)
    {
        // if we want 0 numberOfNodes it is an error, we return null
        if (numberOfNodes == 0)
        {
            return null;
        }

        // Please note if number of nodes is 1, we return the same IP
        // (if we have a first ip 10.0.0.1 and we want to know the last IP
        // with number of nodes = 1, it is the same IP... isn't it?)
        // So, the first we do is subtract 1
        Long numberOfNodesRemain = new Long(numberOfNodes);
        numberOfNodesRemain--;

        if (!ip.isEmpty() && numberOfNodesRemain < new Long("4294967296"))
        {
            StringTokenizer tokenizer = new StringTokenizer(ip, ".");

            // Thanks to the static method we can be sure we have 4 tokens
            // and they live between 0 and 256
            int tokenOne = Integer.parseInt(tokenizer.nextToken());
            int tokenTwo = Integer.parseInt(tokenizer.nextToken());
            int tokenThree = Integer.parseInt(tokenizer.nextToken());
            int tokenFour = Integer.parseInt(tokenizer.nextToken());

            while (numberOfNodesRemain > 0)
            {
                if (numberOfNodesRemain > 16777216)
                {
                    tokenOne = (tokenOne + 1) % 256;
                    numberOfNodesRemain -= 16777216;
                }
                else if (numberOfNodesRemain > 65536)
                {
                    tokenTwo = (tokenTwo + 1) % 256;
                    if (tokenTwo == 0)
                    {
                        tokenOne = (tokenOne + 1) % 256;
                    }
                    numberOfNodesRemain -= 65536;
                }
                else if (numberOfNodesRemain > 256)
                {
                    tokenThree = (tokenThree + 1) % 256;
                    if (tokenThree == 0)
                    {
                        tokenTwo = (tokenTwo + 1) % 256;
                        if (tokenTwo == 0)
                        {
                            tokenOne = (tokenOne + 1) % 256;
                        }
                    }
                    numberOfNodesRemain -= 256;
                }
                else
                {
                    tokenFour = (tokenFour + 1) % 256;

                    if (tokenFour == 0)
                    {
                        tokenThree = (tokenThree + 1) % 256;

                        if (tokenThree == 0)
                        {
                            tokenTwo = (tokenTwo + 1) % 256;
                            if (tokenTwo == 0)
                            {
                                tokenOne = (tokenOne + 1) % 256;
                            }
                        }
                    }
                    numberOfNodesRemain--;
                }

            }

            return IPAddress.newIPAddress(tokenOne + "." + tokenTwo + "." + tokenThree + "."
                + tokenFour);
        }
        else
        {
            return IPAddress.newIPAddress(null);
        }

    }

    /**
     * This method overrides java.lang.Object method toString.
     * 
     * @return IPAddress in String format.
     */
    public String toString()
    {
        return ip;
    }

    /**
     * @return true is ipAddress is equal to "". That means default value for IPAddress constructor
     *         was invalid.
     */
    public boolean isInvalid()
    {
        return ip.isEmpty();
    }

};
