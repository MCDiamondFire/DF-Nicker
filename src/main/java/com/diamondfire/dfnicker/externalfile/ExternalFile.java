package com.diamondfire.dfnicker.externalfile;

import java.io.File;

public enum ExternalFile {
    OTHER_CACHE_DIR(new ExternalFileBuilder()
            .isDirectory(true)
            .setName("other_cache")
            .buildFile()),
    CONFIG(new ExternalFileBuilder()
            .isDirectory(false)
            .setName("config")
            .setFileType("json")
            .buildFile());

    private final File file;

    ExternalFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }


}
