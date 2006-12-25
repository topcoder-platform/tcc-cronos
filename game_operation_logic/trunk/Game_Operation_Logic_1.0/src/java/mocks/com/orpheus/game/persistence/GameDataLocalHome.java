
package com.orpheus.game.persistence;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.StringRefAddr;

import com.orpheus.game.TestObjectFactory;

public class GameDataLocalHome implements Referenceable{
	
//	public void recordRegistation(long userId, long gameId){
//		
//	}
	
	public GameData create(){
		return new MockGameData();
	}

	public Reference getReference() throws NamingException {
		return new Reference(GameDataLocalHome.class.getName(), new StringRefAddr("GameDataLocalHome", "GameDataLocalHome"),
	            TestObjectFactory.class.getName(), null);
	}
 }
