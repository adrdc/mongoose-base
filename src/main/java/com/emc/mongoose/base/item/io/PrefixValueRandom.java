package com.emc.mongoose.base.item.io;

import com.github.akurilov.commons.io.Input;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrefixValueRandom implements Input<String> {

    private final int min;
    private final int max;
    private final ThreadLocal<Random> rnd = ThreadLocal.withInitial(Random::new);
    private final ThreadLocal<StringBuilder> stringBuilder = ThreadLocal.withInitial(StringBuilder::new);
    private static final char[] chars = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public PrefixValueRandom(final String s) {
        // random:min:max
        String[] parts = s.split(":");
        this.min = Integer.parseInt(parts[1]);
        this.max = Integer.parseInt(parts[2]);
        System.out.println("random prefix between lengths " + min + "-" + max);
    }

    @Override
    public String get() {
        StringBuilder sb = stringBuilder.get();
        sb.setLength(0);

        Random r = rnd.get();
        int len = r.nextInt(max - min) + min;
        for (int i = 0; i < len-1; i++) {
            sb.append(chars[r.nextInt(25)]);
        }
        sb.append('Z');
        return sb.toString();
    }

    @Override
    public int get(List<String> buffer, int limit) {
        for (int i = 0; i < limit; i++) {
            buffer.add(get());
        }
        return limit;
    }

    @Override
    public long skip(long count) {
        return 0;
    }

    @Override
    public void reset() {}

    @Override
    public void close() throws Exception {}
}
