/*
* The contents of this file are subject to the Common Public Attribution License

* Version 1.0 (the "License"); you may not use this file except in compliance
* with the License. You may obtain a copy of the License at http://cpal.abiquo.com.



* The License is based on the Mozilla Public License Version 1.1 but Sections 14
* and 15 have been added to cover use of software over a computer network and
* provide for limited attribution for the Original Developer. In addition,



* Exhibit A has been modified to be consistent with Exhibit B.
*
* Software distributed under the License is distributed on an "AS IS" basis,
* WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License



* for the specific language governing rights and limitations under the License.
*
* The Original Code is abiquo,  14 Jul 2008. The Original Developer is the
* Initial Developer. The Initial Developer of the Original Code is Soluciones Grid,



* S.L.. All portions of the code are Copyright © Soluciones Grid, S.L.
* All Rights Reserved.
*/

package net.undf.abicloud.business.managers.notification
{
	import flash.events.Event;
	import flash.events.EventDispatcher;
	
	/**
	 * A Notification is a message sent by server, that is not instantly showed to the user. 
	 * @author Oliver
	 * 
	 */	
	public class Notification extends EventDispatcher
	{
		
		/**
		 * Constructor for Notification class 
		 * @param message The message that this Notification will contain
		 * @param date The Date when this Notification was created. If null
		 * 				current system Date will be taken
		 * @param read Flag that indicates if this Notification is marked as read
		 * 
		 */		
		public function Notification(title:String = "", message:String = "", date:Date = null, read:Boolean = false)
		{
			this._title = title;
			this._message = message;
			
			if(date)
				this._date = date;
			else
				this._date = new Date();
				
			this._read = read;
		}
		
		private var _title:String;
		/**
		* The title for this Notification
		*/		
		[Bindable(event="titleUpdated")]
		public function get title():String
		{
			return this._title;
		}
		
		public function set title(value:String):void
		{
			this._title = value;
			dispatchEvent(new Event("titleUpdated"));
		}
		
		
		private var _message:String;
		/**
		* The message that contains this Notification
		*/		
		[Bindable(event="messageUpdated")]
		public function get message():String
		{
			return this._message;
		}
		
		public function set message(value:String):void
		{
			this._message = value;
			dispatchEvent(new Event("messageUpdated"));
		}
		
		
		private var _date:Date;
		/**
		* The Date when this Notification has been created
		*/		
		[Bindable(event="dateUpdated")]
		public function get date():Date
		{
			return this._date;
		}
		
		public function set date(value:Date):void
		{
			this._date = value;
			dispatchEvent(new Event("dateUpdated"));
		}
		
		private var _read:Boolean;
		/**
		 * Flag that indicates if this Notification has been read
		 */
		[Bindable(event="readUpdated")]
		public function get read():Boolean
		{
			return this._read;
		}
		
		public function set read(value:Boolean):void
		{
			this._read = value;
			dispatchEvent(new Event("readUpdated"));
		}
	}
}