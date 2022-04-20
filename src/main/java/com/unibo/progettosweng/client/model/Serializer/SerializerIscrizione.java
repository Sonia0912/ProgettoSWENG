/**
 *  Classe per serializzare e deserializzare l'oggetto Iscrizione inserito nel db.
 **/
package com.unibo.progettosweng.client.model.Serializer;

import com.unibo.progettosweng.client.model.Iscrizione;
import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.Serializer;
import java.io.IOException;
import java.io.Serializable;

public class SerializerIscrizione implements Serializer<Iscrizione>, Serializable {

    private static final long serialVersionUID = 4L;

    @Override
    public void serialize(DataOutput2 out, Iscrizione value) throws IOException {
        out.writeUTF(value.getStudente());
        out.writeUTF(value.getCorso());
    }

    @Override
    public Iscrizione deserialize(DataInput2 input, int available) throws IOException {
        return new Iscrizione(input.readUTF(),input.readUTF());
    }

}
