package com.wxm.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @author zhaowei
 */
public class ShellTools {
    /**
     * run a system command
     * 
     * @param cmd
     *            要执行的command，如ls/grep x y等
     * @param print
     *            是否打印处理结果
     * @return
     * @throws IOException
     */
    public static boolean exec(String cmd, boolean print) {
        return execBash(cmd, new PrintWriter(System.out));
    }
    
    /**
     * run a system command
     * 
     * @param cmd
     *            要执行的command，如ls/grep x y等
     * @return
     * @throws IOException
     */
    public static String exec(String cmd) {
        StringWriter writer = new StringWriter();
        execBash(cmd, writer);
        writer.flush();
        return writer.toString();
    }


    /**
     * run a system command
     * 
     * @param cmd
     * @param writer
     * @return
     */
    public static boolean execBash(String cmd, Writer writer) {
        if (writer == null) {
            writer = new PrintWriter(System.out);
        }
        BufferedReader inReader = null;
        BufferedReader errReader = null;
        try {
            Process pid = null;
            // 执行Shell命令
            String[] cmds = {
                "/bin/bash", "-c", cmd
            };
            pid = Runtime.getRuntime().exec(cmds);
            if (pid != null) {
                inReader = new BufferedReader(new InputStreamReader(pid.getInputStream()), 1024);
                errReader = new BufferedReader(new InputStreamReader(pid.getErrorStream()), 1024);
                pid.waitFor();
            }
            writer.write("[Shell]$> " + cmd);
            writer.flush();
            boolean empty = true;
            String line = null;
            // 读取Shell的输出内容，并添加到stringBuffer中
            while (inReader != null && (line = inReader.readLine()) != null) {
                if (empty) {
                    writer.write(" %% got result：{{\n");
                    empty = false;
                }
                writer.write(line + "\n");
                writer.flush();
            }
            if (!empty) {
                writer.write("}}\n");
                empty = true;
            }
            writer.flush();
            boolean noError = true;
            while (errReader != null && (line = errReader.readLine()) != null) {
                noError = false;
                if (empty) {
                    writer.write(" %% got error：{{\n");
                    empty = false;
                }
                writer.write(line + "\n");
                writer.flush();
            }
            if (!empty) {
                writer.write("}}\n");
                empty = true;
            }
            writer.write("\n");
            writer.flush();
            return noError;
        } catch (Exception ioe) {
            System.out.println("{Shell} got exception：" + ioe.getMessage());
        } finally {
            if (inReader != null) {
                try {
                    inReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (errReader != null) {
                try {
                    errReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static boolean mkdir(String dir) {
        return exec("mkdir -p " + dir, true);
    }
    
    public static boolean mv(String src, String des) {
        return exec("mv " + src + " " + des, true);
    }
    
    public static boolean cp(String src, String des) {
        return exec("cp -r " + src + " " + des, true);
    }

    public static boolean rm(String file) {
        return exec("rm " + file, true);
    }

    public static boolean rmrf(String file) {
        return exec("rm -rf " + file, true);
    }

    public static boolean sort(String srcfile, String params) {
        return exec("sort " + params + " " + srcfile, true);
    }
}
