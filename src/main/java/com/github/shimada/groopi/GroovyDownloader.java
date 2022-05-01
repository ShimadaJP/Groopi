package com.github.shimada.groopi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;

public class GroovyDownloader {

    private static final String RUNTIME_URL = "https://repo1.maven.org/maven2/org/codehaus/groovy/groovy/3.0.10/groovy-3.0.10.jar";

    public URLClassLoader download() throws IOException {

        String[] segments = RUNTIME_URL.split("/");
        File file = new File(segments[segments.length - 1]);
        if (!file.exists()) {
            URLConnection connection = new URL(RUNTIME_URL).openConnection();

            // copy file
            try (
                    InputStream in = connection.getInputStream();
                    FileOutputStream out = new FileOutputStream(file)
            ) {

                byte[] buffer = new byte[8192];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }

            }
        }

        return new URLClassLoader(new URL[]{file.toURI().toURL()}, getClass().getClassLoader());
    }
}
