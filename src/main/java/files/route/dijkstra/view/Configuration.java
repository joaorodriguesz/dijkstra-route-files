package files.route.dijkstra.view;

import files.route.dijkstra.ConfigFile;
import files.route.dijkstra.FileProcessor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Configuration extends JFrame{

	private static final long serialVersionUID = 1L;
	private JLabel lblRootDiretory;
	private JLabel lblSucesso;
	private JLabel lblErro;
	private JLabel lblCheck;
	private JTextField txtRootDireotry;
	private JTextField txtSucesso;
	private JTextField txtErro;
	private JButton btnSalvar;
	private JCheckBox chkRota;

	public Configuration() {
		setSize(400,320);
		setTitle("Settings");
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		criarComponentes();
	}

	public void criarComponentes (){
		lblRootDiretory = new JLabel("Directory:");
		lblRootDiretory.setBounds(10, 10, 80, 25);
		getContentPane().add(lblRootDiretory);

		txtRootDireotry = new JTextField();
		txtRootDireotry.setBounds(80, 10, 300, 25);
		getContentPane().add(txtRootDireotry);

		lblSucesso = new JLabel("Success:");
		lblSucesso.setBounds(10, 45, 95, 25);
		getContentPane().add(lblSucesso);

		txtSucesso = new JTextField();
		txtSucesso.setBounds(80, 45, 150, 25);
		getContentPane().add(txtSucesso);

		lblErro = new JLabel("Error:");
		lblErro.setBounds(10, 80, 60, 25);
		getContentPane().add(lblErro);

		txtErro = new JTextField();
		txtErro.setBounds(80, 80, 150, 25);
		getContentPane().add(txtErro);

		chkRota = new JCheckBox();
		chkRota.setBounds(80, 110, 20, 25);
		getContentPane().add(chkRota);

		lblCheck = new JLabel("Default");
		lblCheck.setBounds(100, 110, 120, 25);
		getContentPane().add(lblCheck);

		btnSalvar = new JButton("SALVAR");
		btnSalvar.setBounds(80, 140, 100, 25);	
		getContentPane().add(btnSalvar);

		btnSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ConfigFile configFile = new ConfigFile();
				FileProcessor fileProcessor = new FileProcessor();
				String diretory = txtRootDireotry.getText();
				String success = txtSucesso.getText();
				String error = txtErro.getText();
				Boolean automaticDirectory = chkRota.isSelected();

				if(automaticDirectory){
					configFile.createConfigFile();
				} else {
					configFile.createConfigFile(diretory, success, error);
				}

				fileProcessor.readConfigFile(ConfigFile.getDestinationPath());
				fileProcessor.processFiles();

				JOptionPane.showMessageDialog(Configuration.this,
						"Diretory: " + ConfigFile.getDestinationPath() +
								"\nSuccess: " + ConfigFile.getSuccessDirectoryName() +
								"\nError: " + ConfigFile.getFailedDirectoryName() +
								"\nAutomatic file config: " + automaticDirectory);

				dispose();
			}
		});
	}
}

