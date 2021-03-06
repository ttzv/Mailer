package com.ttzv.item.uiUtils;

import com.ttzv.item.file.MailMsgParser;
import javafx.scene.control.Tab;
import javafx.scene.web.WebView;

import java.nio.file.Path;
import java.util.Objects;

public class ViewTab extends Tab {

    private MailMsgParser parser;
    private WebView webView;
    private String name;
    private Path path;

    public ViewTab(String tabName, MailMsgParser parser, Path path){
        webView = new WebView();
        this.name = tabName;
        this.parser = parser;
        this.path = path;
        this.setText(name);
        this.setContent(webView);
        parser.parseFlaggedTopic();
        webView.getEngine().loadContent(String.valueOf(parser.getOutputString()));

       /* this.setOnSelectionChanged(event -> {
            System.out.println("changed");
        });*/
    }

    public void reload(){
        webView.getEngine().loadContent(String.valueOf(parser.getOutputString()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ViewTab viewTab = (ViewTab) o;
        return Objects.equals(name, viewTab.name);
    }


    @Override
    public int hashCode() {
        return Objects.hash(webView, name, parser);
    }

    public MailMsgParser getParser() {
        if(parser!=null) {
            return parser;
        } else {
            throw new NullPointerException("(Mailer) Could not find message, perhaps it is not loaded?");
        }
    }

    public String getName() {
        return name;
    }

    public Path getPath() {
        return path;
    }
}
