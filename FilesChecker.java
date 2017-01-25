package discrepancyfinder;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;

public class FilesChecker implements Runnable, ThreadController {

	private Label status;
	private TextArea resultText;
	private ImageView stopButton;
	private FileInfo excelFile1, excelFile2;
	private ArrayList<Mapping> mapping;
	private boolean terminateThread = false;
	private boolean appendRowIndex = false, appendColumnIndex = false;
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//System.out.println("Thread started.");
		
		try {
			SearchEngine searchEngine = new SearchEngine();
			searchEngine.setMapping(mapping);
			searchEngine.setThreadController(this);
			searchEngine.appendIndexes(appendRowIndex, appendColumnIndex);
						
			showStopButton();
			
			searchEngine.searchDiscrepency(excelFile1, excelFile2);
			
			ArrayList<DiscrepencyResult> result = searchEngine.getResult();
			String outputMessage;
			
			if (result.isEmpty())
				outputMessage = "No Result Found";
			else
				outputMessage = String.format("Discrepency:\nFile 1 = %s\nFile 2 = %s\n%-35s%-20s%-5s%-20s\n", 
						excelFile1.excelFile.getName(), excelFile2.excelFile.getName(), " ", "File 1", " ", "File 2");
			
			for (DiscrepencyResult r: result)
				outputMessage = outputMessage + String.format("%-35s%-20s%-5s%-20s\n", r.colName + " - " + r.rowName + ": ", r.file1Data, " ", r.file2Data);
			
			
			if (!result.isEmpty())
				outputMessage = outputMessage + "\n========== End ==========";
			
			outputResult(outputMessage);
			
			hideStopButton();
			
		} catch (InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Alert(AlertType.WARNING, "Cannot load excel files.").showAndWait();
			
		}
		
		
		
	}
	
	
	public void setRequiredElements(Label status, ImageView stopButton, TextArea resultText, FileInfo excelFile1, FileInfo excelFile2, ArrayList<Mapping> mapping) {
		
		this.status = status;
		this.stopButton = stopButton;
		this.resultText = resultText;
		this.excelFile1 = excelFile1;
		this.excelFile2 = excelFile2;
		this.mapping = mapping;
		
	}
	
	public void appendIndexes(boolean appendRowIndex, boolean appendColumnIndex) {
		
		this.appendRowIndex = appendRowIndex;
		this.appendColumnIndex = appendColumnIndex;
		
	}
	
	public void terminate(boolean terminateThread) {
		
		this.terminateThread = terminateThread;
		
	}
	
	public void updateStatus(String message) {
		
		Thread updateUIThread = new Thread(new Runnable() {
			public void run() {
				status.setText(message);
				
			}
			
		});
		
		Platform.runLater(updateUIThread);
				
	}
	
	public boolean terminateThread() {
		return terminateThread;
		
	}
	
	private void outputResult(String outputMessage) {
		
		Thread updateUIThread = new Thread(new Runnable() {
			public void run() {
				resultText.setText(outputMessage);
				
			}
			
		});
		
		Platform.runLater(updateUIThread);
		
	}
	
	private void showStopButton() {
		Thread updateUIThread = new Thread(new Runnable() {
			public void run() {
				stopButton.setVisible(true);
								
			}
			
		});
		
		Platform.runLater(updateUIThread);
		
	}
	
	private void hideStopButton() {
		Thread updateUIThread = new Thread(new Runnable() {
			public void run() {
				stopButton.setVisible(false);
								
			}
			
		});
		
		Platform.runLater(updateUIThread);
		
	}
	
}
