package com.unibo.progettosweng.client.model.Serializer;


import com.unibo.progettosweng.client.model.Utente;

import java.io.IOException;
import java.io.Serializable;

import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.Serializer;

public class SerializerUtente implements Serializer<Utente>, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public void serialize(DataOutput2 out, Utente value) throws IOException {
        out.writeUTF(value.getNome());
        out.writeUTF(value.getCognome());
        out.writeUTF(value.getUsername());
        out.writeUTF(value.getPassword());
        out.writeUTF(value.getTipo());
    }

    @Override
    public Utente deserialize(DataInput2 input, int available) throws IOException {
        return new Utente(input.readUTF(),input.readUTF(),input.readUTF(),input.readUTF(),input.readUTF()  );
    }
}