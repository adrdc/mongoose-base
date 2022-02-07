package com.emc.mongoose.base.item.io;

import com.github.akurilov.commons.io.Input;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PrefixValueFromFileInputImpl implements Input<String> {

    private int current = 0;
    private final List<String> prefixList;

    public PrefixValueFromFileInputImpl(final File f) {
        this.prefixList = readFromFile(f);
    }

    private List<String> readFromFile(File f) {
        List<String> list = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return list;
    }

    @Override
    public String get() {
        current = ++current % prefixList.size();
        return prefixList.get(current);
    }

    @Override
    public int get(List<String> buffer, int limit) {
        int n = Math.min(prefixList.size() - current, limit);
        for (int i = 0; i < limit; i++) {
            buffer.addAll(prefixList.subList(current, current + n));
        }
        current += n;
        current = current % prefixList.size();
        return n;
    }

    @Override
    public long skip(long count) {
        return 0;
    }

    @Override
    public void reset() {
        current = 0;
    }

    @Override
    public void close() throws Exception {

    }
}
