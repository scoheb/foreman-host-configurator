package com.scoheb.foreman.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shebert on 10/01/17.
 */
public class Main {

    private static Logger LOGGER = Logger.getLogger(List.class);

    @Parameter(names = "-debug", description = "Debug mode")
    private boolean debug = false;

    @Parameter(names = "--help", help = true)
    private boolean help = false;

    public static void main(String[] args) {
        new Main(args);
    }

    @SuppressFBWarnings("DM_EXIT")
    public Main(String[] args) {
        JCommander jc = new JCommander(this);
        Map<String, Command> commands = new HashMap<String, Command>();
        jc.setProgramName("foreman-host-configurator");

        ListHosts listHosts = new ListHosts();
        jc.addCommand("list", listHosts);
        commands.put("list", listHosts);

        CreateFromFile createFromFile = new CreateFromFile();
        jc.addCommand("create", createFromFile);
        commands.put("create", createFromFile);

        Release release = new Release();
        jc.addCommand("release", release);
        commands.put("release", release);

        try {
            jc.parse(args);
        }
        catch (ParameterException pe) {
            LOGGER.error(pe.getMessage());
            jc.usage();
            System.exit(3);
        }
        if (help) {
            jc.usage();
            System.exit(2);
            return;
        }
        if (debug) {
            LOGGER.getRootLogger().setLevel(Level.ALL);
        }
        try {
            String commandToRun = jc.getParsedCommand();
            if (commandToRun == null || commandToRun.equals("")) {
                throw new RuntimeException("No command passed. Run with --help to see usage.");
            }
            commands.get(commandToRun).run();
        }
        catch (Exception pe) {
            if (debug) {
                LOGGER.error(pe.getMessage(), pe);
            } else {
                LOGGER.error(pe.getMessage());
            }
            System.exit(1);
        }
        System.exit(0);

    }
}