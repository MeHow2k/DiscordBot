package mehow2k.botdiscord.UI;

import mehow2k.botdiscord.C;
import mehow2k.botdiscord.Listeners;
import mehow2k.botdiscord.commands.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;


public class Panel extends JPanel implements ActionListener {

    static JTextArea textArea;
    JButton buttonStart;
    JButton buttonStop;
    static JButton buttonSettings;
    JLabel label;
    JScrollPane jScrollPane;

    //JDA classes
    JDA jda;
    JDABuilder jdaBuilder;
   public Panel(){
        super(null);
        textArea = new JTextArea();
        textArea.setEditable(false);
        jScrollPane = new JScrollPane(textArea);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane.setBounds(10, 100, 500, 500);
        buttonStart= new JButton("Start");
        buttonStart.setBounds(30,10,100,50);
        buttonStart.addActionListener(this);
        buttonStop= new JButton("Stop");
        buttonStop.setBounds(150,10,100,50);
        buttonStop.addActionListener(this);
        buttonSettings= new JButton("Settings");
        buttonSettings.setBounds(270,10,100,50);
        buttonSettings.addActionListener(this);
        label= new JLabel("Console log");
        label.setBounds(10,60,100,50);
        buttonStop.setEnabled(false);

        add(buttonStart);
        add(buttonStop);
        add(buttonSettings);
        //add(textArea);
        add(label);
        add(jScrollPane);
    }

    public static void printLog(String text){
       if(C.isUI) textArea.append("\n"+text);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o=e.getSource();
        if(o==buttonStart){
            Panel.printLog("Building API...");
            //utworzenie obiektu JDA z tablica przywilejow
        jdaBuilder=JDABuilder.create(C.TOKEN, Arrays.asList(C.gatewayIntents));
//        JDABuilder jdaBuilder=JDABuilder.createDefault(TOKEN);
        jda = jdaBuilder.build();

        //dodanie nasluchiwania wydarzeń discorda
        jda.addEventListener(new Listeners());
        jda.addEventListener(new SlashCommands());
        jda.addEventListener(new Play());
        jda.addEventListener(new Skip());
        jda.addEventListener(new Stop());
        jda.addEventListener(new Queue());
        jda.addEventListener(new ClearQueue());
        jda.addEventListener(new NowPlaying());

        buttonStart.setEnabled(false);
        buttonStop.setEnabled(true);
        }
        if(o==buttonStop){
            if (jda != null){
                Panel.printLog("Shutting down...");
                jda.shutdown();
                Panel.printLog("Closed.");
            }
            buttonStart.setEnabled(true);
            buttonStop.setEnabled(false);
        }
        if(o==buttonSettings){
            openSettingsWindow();
            buttonSettings.setEnabled(false);
        }
    }
    //utworzenie elementów GUI okna ustawień
    public void openSettingsWindow(){
        JFrame settings = new JFrame("Settings");
        settings.setSize(350, 500);
        settings.setVisible(true);
        settings.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width+400) / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);
         PanelSettings ps = new PanelSettings();
         settings.setContentPane(ps);
        settings.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                allowOpenSettings();
                settings.dispose();
            }
        });
    }
    public static void allowOpenSettings(){
       buttonSettings.setEnabled(true);
    }
}
