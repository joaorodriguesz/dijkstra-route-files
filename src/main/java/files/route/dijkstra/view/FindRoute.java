package files.route.dijkstra.view;

import files.route.dijkstra.graph.GraphFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class FindRoute extends JFrame{

	private static final long serialVersionUID = 1L;

	private JLabel lblSearch;

	private JTextField txtSearch;

	private JButton btnProcess;

	private JButton btnSearch;

	public FindRoute() {
		setSize(800, 100);
		setTitle("Searching");
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		createComponents();
	}

	private void createComponents() {
		lblSearch = new JLabel("SEARCH");
		lblSearch.setBounds(5, 10 , 80, 25);
		getContentPane().add(lblSearch);

		txtSearch = new JTextField();
		txtSearch.setBounds(60, 10, 440, 25);
		getContentPane().add(txtSearch);

		btnProcess = new JButton(new AbstractAction(("PROCESS")) {
			@Override
			public void actionPerformed(ActionEvent e) {
				var graph = GraphFactory.createGraphFromRouteFile(txtSearch.getText());
				var source = JOptionPane.showInputDialog("SOURCE ex(A1)");
				var target = JOptionPane.showInputDialog("TARGET ex(B2)");
				Double distance = GraphFactory.shortestDistance(graph, source, target);
				JOptionPane.showMessageDialog(null, source +" => "+target+" = " +distance+"km");
			}
		});
		btnProcess.setBounds(620, 10, 160, 25);
		getContentPane().add(btnProcess);

		btnSearch = new JButton("SEARCH");
		btnSearch.setBounds(510, 10, 100, 25);
		getContentPane().add(btnSearch);

		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int result = chooser.showOpenDialog(btnSearch);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFolder = chooser.getSelectedFile();
					txtSearch.setText(selectedFolder.getAbsolutePath());
				}
			}
		});
	}
}
