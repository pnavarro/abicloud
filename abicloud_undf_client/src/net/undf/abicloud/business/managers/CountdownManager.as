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

package net.undf.abicloud.business.managers
{
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.TimerEvent;
	import flash.utils.Timer;
	
	/**
	 * Simple countdown manager
	 * 
	 * This class provides a countdown, and can be used to display a countdown from any number of seconds
	 * The countdown can be repeated any number of times, and everytime the countdown is reached, an event
	 * will be dispatched
	 * 
	 * In the future, this class should be multiton, since CountdownManager could offer multiple countdowns to different
	 * consumers at the same time
	 * 
	 * @author Oliver
	 * 
	 */	
	public class CountdownManager extends EventDispatcher
	{
		
		public static const COUNTDOWN_COMPLETE_EVENT:String = "countdownCompleteCountdownManager";
		
		private var _timer:Timer;
		
		private var _currentSeconds:Number;
		
		private var _startTime:Number = 0;
		
		public function CountdownManager()
		{
			//Initializing timer for the first time, and setting listeners
			this._timer = new Timer(1000, 0);
			
			this._timer.addEventListener(TimerEvent.TIMER, timerHandler);
			this._timer.addEventListener(TimerEvent.TIMER_COMPLETE, timerCompleteHandler);
		}
		
		/**
		 * Starts the countdown. The current state of the countdown can be watched using the property currentSeconds
		 * @param startTime The start time (in seconds) for the countdown
		 * @param repeatCount The number of times for the countdown. 0 means infinite
		 * 
		 */		
		public function startAutoCountdown(startTime:Number, repeatCount:Number = 0):void
		{
			if(this._timer.running)
			{
				this._timer.stop();
				dispatchEvent(new Event("clockRunningUpdate"));
			}
			
			this._timer.repeatCount = 1000 * startTime * repeatCount;

			this._startTime = startTime;
			this._currentSeconds = startTime;
			dispatchEvent(new Event("currentSecondsUpdate"));
			
			this._timer.start();
			dispatchEvent(new Event("clockRunningUpdate"));
		}
		
		/**
		 * Stops the current countdown 
		 * 
		 */		
		public function stopAutoCountdown():void
		{
			if(this._timer && this._timer.running)
			{
				this._timer.stop();
				this._currentSeconds = this._startTime;
				dispatchEvent(new Event("clockRunningUpdate"));
			}
		}
		
		private function timerHandler(timerEvent:TimerEvent):void
		{
			if(this._currentSeconds == 0)
			{
				this._currentSeconds = this._startTime;
				dispatchEvent(new Event(CountdownManager.COUNTDOWN_COMPLETE_EVENT));
			}
			else
				this._currentSeconds = this._currentSeconds - 1;
				
			dispatchEvent(new Event("currentSecondsUpdate"));
		}
		
		private function timerCompleteHandler(timerEvent:TimerEvent):void
		{
			this._timer.stop();
			dispatchEvent(new Event("clockRunningUpdate"));
			
			this._currentSeconds = 0;
			dispatchEvent(new Event("currentSecondsUpdate"));
		}
		
		[Bindable(event="clockRunningUpdate")]
		public function get clockRunning():Boolean
		{
			return this._timer.running;
		}
		
		[Bindable(event="currentSecondsUpdate")]
		public function get currentSeconds():Number
		{
			return this._currentSeconds;
		}
	}
}