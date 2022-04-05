package com.unibo.progettosweng.client.model.Serializer;

import com.unibo.progettosweng.client.model.Valutazione;
import org.jetbrains.annotations.NotNull;
import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.Serializer;

import java.io.IOException;
import java.io.Serializable;

public class SerializerValutazione implements Serializer<Valutazione>, Serializable {
    @Override
    public void serialize(@NotNull DataOutput2 out, @NotNull Valutazione valutazione) throws IOException {
        out.writeUTF(valutazione.getNomeCorso());
        out.writeUTF(valutazione.getStudente());
        out.writeInt(valutazione.getVoto());
        out.writeInt(valutazione.getStato());
    }

    @Override
    public Valutazione deserialize(@NotNull DataInput2 in, int i) throws IOException {
        return new Valutazione(in.readUTF(),in.readUTF(),in.readInt(),in.readInt());
    }
}
