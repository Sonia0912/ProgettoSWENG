/**
 *  Classe per serializzare e deserializzare l'oggetto Registrazione inserito nel db.
 **/
package com.unibo.progettosweng.client.model.Serializer;

import com.unibo.progettosweng.client.model.Registrazione;
import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.Serializer;

import java.io.IOException;
import java.io.Serializable;

public class SerializerRegistrazione implements Serializer<Registrazione>, Serializable {

    private static final long serialVersionUID = 5L;

    @Override
    public void serialize(DataOutput2 out, Registrazione value) throws IOException {
        out.writeUTF(value.getStudente());
        out.writeUTF(value.getCorso());
    }

    @Override
    public Registrazione deserialize(DataInput2 input, int available) throws IOException {
        return new Registrazione(input.readUTF(),input.readUTF());
    }

}
