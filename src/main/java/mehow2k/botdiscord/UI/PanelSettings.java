package mehow2k.botdiscord.UI;

import mehow2k.botdiscord.C;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PanelSettings extends JPanel implements ActionListener {
    JTextArea tokenTextArea,guildIDTextArea,chatGPTkeyTextArea;
    JLabel tokenLabel,guildIDLabel,chatGPTkeyLabel;
    JButton buttonSaveAndQuit,buttonBack;

    PanelSettings(){
        super(null);

        tokenLabel = new JLabel();
        tokenLabel.setBounds(100, 10, 200, 25);
        tokenLabel.setText("Discord TOKEN:");

        tokenTextArea = new JTextArea(C.TOKEN);
        tokenTextArea.setBounds(10, 35,300,70);
        tokenTextArea.setLineWrap(true);

        guildIDLabel = new JLabel();
        guildIDLabel.setBounds(100, 140, 200, 25);
        guildIDLabel.setText("Discord Server ID:");

        guildIDTextArea = new JTextArea(String.valueOf(C.GuildID));
        guildIDTextArea.setBounds(10, 170,300,70);
        guildIDTextArea.setLineWrap(true);

        chatGPTkeyLabel = new JLabel();
        chatGPTkeyLabel.setBounds(100, 245, 200, 25);
        chatGPTkeyLabel.setText("ChatGPT API Key:");

        chatGPTkeyTextArea = new JTextArea(C.ChatGPTapikey);
        chatGPTkeyTextArea.setBounds(10, 280,300,70);
        chatGPTkeyTextArea.setLineWrap(true);

        buttonSaveAndQuit = new JButton("Save and Quit");
        buttonSaveAndQuit.setBounds(30,380,120,50);

        buttonBack = new JButton("Back");
        buttonBack.setBounds(160,380,120,50);

        buttonBack.addActionListener(this);
        buttonSaveAndQuit.addActionListener(this);

        add(tokenLabel);
        add(guildIDLabel);
        add(chatGPTkeyLabel);
        add(tokenTextArea);
        add(guildIDTextArea);
        add(chatGPTkeyTextArea);
        add(buttonBack);
        add(buttonSaveAndQuit);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o=e.getSource();
        if(o==buttonBack){
            //close windopw
            Panel.allowOpenSettings();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.dispose();
        }
        if(o==buttonSaveAndQuit){
            boolean isCorrect=true;
            C.TOKEN=tokenTextArea.getText();
            try{
                C.GuildID= Long.valueOf(guildIDTextArea.getText());
            }catch (NumberFormatException nfe){
                JOptionPane.showMessageDialog(this, "GuildID must be a number!");
                isCorrect=false;
            }
            C.ChatGPTapikey= chatGPTkeyTextArea.getText();
            if(isCorrect){
            //save to config file
                updateSettings();
            //close windopw
                Panel.allowOpenSettings();
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                frame.dispose();
            }
        }
    }
    public void updateSettings(){
        try {
            //otwarcie pliku ustawień
            File config = new File("config.txt");
            FileWriter out = new FileWriter(config);
            //wpisanie aktualnych ustawień do pliku ustawień
            out.write(C.TOKEN + "\n" + C.GuildID+"\n"+C.ChatGPTapikey);
            out.close();
        } catch (IOException ee) {
            ee.printStackTrace();
        }
    }
}
