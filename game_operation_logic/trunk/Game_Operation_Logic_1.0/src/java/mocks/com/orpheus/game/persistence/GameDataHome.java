
package com.orpheus.game.persistence;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.StringRefAddr;

import com.orpheus.game.TestObjectFactory;

public class GameDataHome implements Referenceable{
	
//	public void recordRegistation(long userId, long gameId){
//		
//	}
	
	public GameData create(){
		return new MockGameData();
	}

	public Reference getReference() throws NamingException {
		return new Reference(GameDataHome.class.getName(), new StringRefAddr("GameDataHome", "GameDataHome"),
	            TestObjectFactory.class.getName(), null);
	}
 }
