package com.unibo.progettosweng.server;

import com.unibo.progettosweng.model.Valutazione;
import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.Serializer;

import java.io.IOException;
import java.io.Serializable;

public class SerializerValutazione implements Serializer<Valutazione>, Serializable {

    private static final long serialVersionUID = 4L;

    @Override
    public void serialize(DataOutput2 out, Valutazione value) throws IOException {
        out.writeUTF(value.getNomeCorso());
        out.writeUTF(value.getUsernameStudente());
        out.write(value.getVoto());
    }

    @Override
    public Valutazione deserialize(DataInput2 input, int available) throws IOException {
        return new Valutazione(input.readUTF(),input.readUTF(),input.readInt() );
    }
}