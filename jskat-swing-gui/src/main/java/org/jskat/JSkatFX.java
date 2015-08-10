/**
 * This file is part of JSkat.
 *
 * JSkat is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JSkat is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JSkat.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jskat;

import javax.swing.SwingUtilities;

import org.apache.log4j.PropertyConfigurator;
import org.jskat.control.JSkatMaster;
import org.jskat.data.DesktopSavePathResolver;
import org.jskat.data.JSkatOptions;
import org.jskat.gui.img.JSkatGraphicRepository;
import org.jskat.gui.swing.JSkatViewImpl;
import org.jskat.gui.swing.LookAndFeelSetter;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class JSkatFX extends Application {
	
	public static void main(String[] args) {
		
        PropertyConfigurator.configure(ClassLoader
                .getSystemResource("org/jskat/config/log4j.properties")); //$NON-NLS-1$
        JSkatOptions.instance(new DesktopSavePathResolver());
		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Hello World!");
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				LookAndFeelSetter.setLookAndFeel();
			}
		});
		
		JSkatViewImpl jskatView = new JSkatViewImpl();
		JSkatGraphicRepository.INSTANCE.toString();
		JSkatMaster.INSTANCE.setView(jskatView);
		
		SwingNode swingNode = new SwingNode();
		swingNode.setContent(jskatView.mainPanel);
		
		StackPane pane = new StackPane();
        pane.getChildren().add(swingNode);
        
		Scene scene = new Scene(pane,
				JSkatOptions.instance().getMainFrameSize().getWidth(),
				JSkatOptions.instance().getMainFrameSize().getHeight());
		scene.widthProperty()
				.addListener(new javafx.beans.value.ChangeListener<Number>() {
					@Override
					public void changed(
							ObservableValue<? extends Number> observable,
							Number oldValue, Number newValue) {
						System.out.println("Width: " + newValue);
						JSkatOptions.instance()
								.setMainFrameWidth(newValue.intValue());
					}
				});
		scene.heightProperty()
				.addListener(new javafx.beans.value.ChangeListener<Number>() {
					@Override
					public void changed(
							ObservableValue<? extends Number> observable,
							Number oldValue, Number newValue) {
						System.out.println("Height: " + newValue);
						JSkatOptions.instance()
								.setMainFrameHeight(newValue.intValue());
					}
				});

		primaryStage.setScene(scene);

		primaryStage
				.setX(JSkatOptions.instance().getMainFramePosition().getX());
		primaryStage
				.setY(JSkatOptions.instance().getMainFramePosition().getY());

		primaryStage.show();
	}
}