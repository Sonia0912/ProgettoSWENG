package com.unibo.progettosweng.shared;

import com.unibo.progettosweng.shared.model.Insegnamento;
import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.Serializer;
import java.io.IOException;
import java.io.Serializable;

public class SerializerInsegnamento implements Serializer<Insegnamento>, Serializable {

    private static final long serialVersionUID = 5L;

    @Override
    public void serialize(DataOutput2 out, Insegnamento value) throws IOException {
        out.writeUTF(value.getNomeDocente());
        out.writeUTF(value.getNomeCorso());
        out.writeUTF(value.getTipologia());

    }

    @Override
    public Insegnamento deserialize(DataInput2 input, int available) throws IOException {
        return new Insegnamento(input.readUTF(),input.readUTF(),input.readUTF() );
    }
}