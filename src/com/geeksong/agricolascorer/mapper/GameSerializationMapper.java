package com.geeksong.agricolascorer.mapper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import com.geeksong.agricolascorer.model.Game;

public class GameSerializationMapper {
    public static byte[] serialize(Game game) throws IOException {
    	ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
    	ObjectOutputStream objectStream = new ObjectOutputStream(byteArrayStream);
    	objectStream.writeObject(game);
    	objectStream.close();
    	return byteArrayStream.toByteArray();
    }
    
    public static Game deserialize(byte[] gameBytes) throws StreamCorruptedException, IOException, ClassNotFoundException {
    	ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(gameBytes);
    	ObjectInputStream objectStream = new ObjectInputStream(byteArrayStream);
    	Game game = (Game) objectStream.readObject();
    	objectStream.close();
    	return game;
    }
}	
