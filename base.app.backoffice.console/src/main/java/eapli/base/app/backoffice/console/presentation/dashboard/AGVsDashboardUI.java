package eapli.base.app.backoffice.console.presentation.dashboard;

import eapli.base.dashboard.application.AGVsDashboardController;
import eapli.framework.presentation.console.AbstractUI;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class AGVsDashboardUI extends AbstractUI {

    AGVsDashboardController CTRL = new AGVsDashboardController();

    @Override
    protected boolean doShow() {

        CTRL.showDashboard();

        URI uri;
        try {
            uri = new URI("https://localhost:11557/");
            Desktop.getDesktop().browse(uri);
        } catch (HeadlessException | AWTError | URISyntaxException | IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public String headline() {
        return "Dash";
    }
}
