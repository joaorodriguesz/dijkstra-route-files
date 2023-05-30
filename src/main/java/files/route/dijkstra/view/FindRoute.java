package files.route.dijkstra.view;

import files.route.dijkstra.ConfigFile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.util.List;

public class FindRoute extends JFrame{

	private static final long serialVersionUID = 1L;
	private JLabel lblSearch;
	private JLabel lblOriginCode;
	private JLabel lblOriginCity;
	private JLabel lblOriginCityDesc;
	private JLabel lblDestinationCity;
	private JLabel lblDestinationCityDesc;
	private JLabel lblDestinationCode;
	private JLabel lblKM;
	private JTextField txtSearch;
	private JTextField txtOriginCode;
	private JTextField txtOriginCity;
	private JTextField txtDestinationCity;
	private JTextField txtDestinationCode;
	private JTextField txtKM;
	private JButton btnSave;
	private JButton btnProcess;
	private JButton btnSearch;
	private JButton btnAdd;
	private JTable tblData;
	private JScrollPane spnData;
	private DefaultTableModel model;

	Graph g = new Graph(999);

	public FindRoute() {
		setSize(800, 500);
		setTitle("Busca Pelo Melhor Caminho");
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		createComponents();
	}

	public void clearFields() {
		txtOriginCode.setText("");
		txtOriginCity.setText("");
		txtDestinationCode.setText("");
		txtDestinationCity.setText("");
		txtKM.setText("");
	}

	private boolean loaded = false;

	private void loadFromConfigFile(String filePath) {
		if (loaded) {
			JOptionPane.showMessageDialog(null, "Os dados já foram carregados anteriormente.");
			return;
		}

		try {
			File file = new File(filePath);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;

			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				if (data.length == 5) {
					String originCode = data[0];
					String originCity = data[1];
					String destinationCode = data[2];
					String destinationCity = data[3];
					String distance = data[4];

					model.addRow(new Object[] { originCode, originCity, destinationCode, destinationCity, distance });
				}
			}

			reader.close();
			loaded = true;

			JOptionPane.showMessageDialog(null, "Dados carregados com sucesso.");
		} catch (IOException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo: " + ex.getMessage());
		}
	}

	private void createComponents() {
		lblSearch = new JLabel("Buscar");
		lblSearch.setBounds(10, 10 , 80, 25);
		getContentPane().add(lblSearch);

		txtSearch = new JTextField();
		txtSearch.setBounds(60, 10, 625, 25);
		getContentPane().add(txtSearch);

		btnSearch = new JButton("Buscar");
		btnSearch.setBounds(690, 10, 100, 25);
		getContentPane().add(btnSearch);

		lblOriginCode = new JLabel("Código:");
		lblOriginCode.setBounds(10, 50 , 60, 25);
		getContentPane().add(lblOriginCode);

		txtOriginCode = new JTextField();
		txtOriginCode.setBounds(60, 50, 75, 25);
		getContentPane().add(txtOriginCode);

		lblOriginCity = new JLabel("Cidade:");
		lblOriginCity.setBounds(180, 50 , 60, 25);
		getContentPane().add(lblOriginCity);

		txtOriginCity = new JTextField();
		txtOriginCity.setBounds(230, 50, 130, 25);
		getContentPane().add(txtOriginCity);

		lblOriginCityDesc = new JLabel("(ORIGEM)");
		lblOriginCityDesc.setBounds(365, 50 , 60, 25);
		getContentPane().add(lblOriginCityDesc);

		lblDestinationCode = new JLabel("Código:");
		lblDestinationCode.setBounds(10, 80 , 60, 25);
		getContentPane().add(lblDestinationCode);

		txtDestinationCode = new JTextField();
		txtDestinationCode.setBounds(60, 80, 75, 25);
		getContentPane().add(txtDestinationCode);

		lblDestinationCity = new JLabel("Cidade:");
		lblDestinationCity.setBounds(180, 80 , 60, 25);
		getContentPane().add(lblDestinationCity);

		txtDestinationCity = new JTextField();
		txtDestinationCity.setBounds(230, 80, 130, 25);
		getContentPane().add(txtDestinationCity);

		lblDestinationCityDesc = new JLabel("(DESTINO)");
		lblDestinationCityDesc.setBounds(365, 80 , 60, 25);
		getContentPane().add(lblDestinationCityDesc);

		lblKM = new JLabel("KM:");
		lblKM.setBounds(10, 110 , 60, 25);
		getContentPane().add(lblKM);

		txtKM = new JTextField();
		txtKM.setBounds(60, 110, 45, 25);
		getContentPane().add(txtKM);

		btnAdd = new JButton(new AbstractAction("+") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (txtKM.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Campo <KM> Obrigatório!");
				} else if (Double.parseDouble(txtKM.getText()) <= -1) {
					JOptionPane.showMessageDialog(null, "Campo <KM> não pode ser Negativo!");
				} else if (txtOriginCode.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Campo <Código Origem> Obrigatório!");
				} else if (txtOriginCity.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Campo <Cidade Origem> Obrigatório!");
				} else if (txtDestinationCode.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Campo <Cidade Destino> Obrigatório!");
				} else if (txtDestinationCity.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Campo <Cidade Destino> Obrigatório!");
				} else if (txtOriginCity.getText().equals(txtDestinationCity.getText())) {
					JOptionPane.showMessageDialog(null, "Campos <Cidade Origem> e <Cidade Destino> não podem ser iguais");
				} else if (txtOriginCode.getText().equals(txtDestinationCode.getText())) {
					JOptionPane.showMessageDialog(null, "Campos <Código Origem> e <Código Destino> não podem ser iguais");
				} else {
					try {
						int distanceValue = Integer.parseInt(txtKM.getText());
						model.addRow(new Object[] {txtOriginCode.getText(), txtOriginCity.getText(), txtDestinationCode.getText(), txtDestinationCity.getText(), distanceValue});
						g.createEdge(Integer.parseInt(txtOriginCode.getText()), Integer.parseInt(txtDestinationCode.getText()), distanceValue);
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Valor inválido no campo <KM>!");
					} catch (InvalidAlgorithmParameterException e1) {
						e1.printStackTrace();
					}
				}
				clearFields();
			}
		});

		btnAdd.setBounds(524, 115, 45, 25);
		getContentPane().add(btnAdd);

		model = new DefaultTableModel();
		model.addColumn("Código Origem");
		model.addColumn("Cidade Origem");
		model.addColumn("Código Destino");
		model.addColumn("Cidade Destino");
		model.addColumn("Distância");

		tblData = new JTable(model);
		spnData = new JScrollPane(tblData);
		spnData.setBounds(10, 145, 565, 280);
		getContentPane().add(spnData);

		btnSave = new JButton("SALVAR");
		btnSave.setBounds(360, 430, 100, 25);
		getContentPane().add(btnSave);

		btnProcess = new JButton(new AbstractAction(("PROCESSAR")) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				try {

					int origin = Integer.parseInt(JOptionPane.showInputDialog("Origem"));
					if(origin <= 0) {
						JOptionPane.showMessageDialog(null,"O campo código origem não pode ser menor que zero! Reprocesse!");
						return;
					}

					int destiny = Integer.parseInt(JOptionPane.showInputDialog("Destino"));
					if(destiny <= 0) {
						JOptionPane.showMessageDialog(null,"O campo código destino não pode ser menor que zero! Reprocesse!");
						return;
					}

					else {
						List<Integer> result = g.shortestPath(origin, destiny);

						JOptionPane.showMessageDialog(null, "Confira o resultado: "+ result);
					}

				}
				catch (NumberFormatException err){
					JOptionPane.showMessageDialog(null, "Os campos código origem e código destino não podem estar vazio! Reprocesse!");
				}
			}
		});
		btnProcess.setBounds(465, 430, 110, 25);
		getContentPane().add(btnProcess);

		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//loadFromConfigFile(ConfigFile.getConfigFilePath());
					JFileChooser chooser = new JFileChooser();
					int result = chooser.showOpenDialog(btnSearch);
					if (result == JFileChooser.APPROVE_OPTION) {
						File selectedFolder = chooser.getSelectedFile();
						txtSearch.setText(selectedFolder.getAbsolutePath());
					}

			}
		});

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filePath = "C:\\Teste\\config.txt";

				try {
					File file = new File(filePath);
					FileWriter writer = new FileWriter(file, true);
					BufferedWriter bufferedWriter = new BufferedWriter(writer);

					for (int i = 0; i < model.getRowCount(); i++) {
						String originCode = (String) model.getValueAt(i, 0);
						String originCity = (String) model.getValueAt(i, 1);
						String destinationCode = (String) model.getValueAt(i, 2);
						String destinationCity = (String) model.getValueAt(i, 3);
						String distance = model.getValueAt(i, 4).toString();

						String line = originCode + "," + originCity + "," + destinationCode + "," + destinationCity + "," + distance;
						bufferedWriter.write(line);
						bufferedWriter.newLine();
					}

					bufferedWriter.close();
					writer.close();
				} catch (IOException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Erro ao salvar o arquivo: " + ex.getMessage());
				}
			}
		});
	}
}
