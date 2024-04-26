package com.hibernate.gui;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.hibernate.dao.PeliculaDAO;
import com.hibernate.model.Pelicula;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;

public class App {

	private JFrame frame;
	private JTextField tfId;
	private JTextField tfPelicula;
	private JTextField tfTemporada;
	private JTextField tfcapitulos;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		initialize();
	}

	public void rellenarTabla(DefaultTableModel model) {
		PeliculaDAO dao = new PeliculaDAO();
		List<Pelicula> peliculas = dao.selectAllPelicula();
		if (model.getRowCount() > 0) {
			model.setRowCount(0);
		}
		for (Pelicula p : peliculas) {
			Object[] row = new Object[4];
			row[0] = p.getId();
			row[1] = p.getNombre();
			row[2] = p.getTemporadas();
			row[3] = p.getCapitulos();

			model.addRow(row);
		}

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		PeliculaDAO dao = new PeliculaDAO();
		frame = new JFrame();
		frame.setBounds(100, 100, 875, 545);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		DefaultTableModel modelPeliculas = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		modelPeliculas.addColumn("ID");
		modelPeliculas.addColumn("Titulo");
		modelPeliculas.addColumn("Nºtemporadas");
		modelPeliculas.addColumn("Total episodios");

		JTable tablaPeliculas = new JTable(modelPeliculas);
		tablaPeliculas.setBounds(454, 1, 340, 0);
		frame.getContentPane().add(tablaPeliculas);
		tablaPeliculas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane sPBici = new JScrollPane(tablaPeliculas);
		sPBici.setBounds(59, 30, 746, 259);
		sPBici.setEnabled(false);
		frame.getContentPane().add(sPBici);
		frame.getContentPane().setLayout(null);

		JLabel lblId = new JLabel("ID:");
		lblId.setBounds(59, 325, 70, 15);
		frame.getContentPane().add(lblId);

		JLabel lblPelicula = new JLabel("Pelicula:");
		lblPelicula.setBounds(59, 352, 70, 15);
		frame.getContentPane().add(lblPelicula);

		JLabel lblId_1_1 = new JLabel("Temporada:");
		lblId_1_1.setBounds(59, 379, 102, 15);
		frame.getContentPane().add(lblId_1_1);

		JLabel lblId_1_1_1 = new JLabel("Capitulos:");
		lblId_1_1_1.setBounds(59, 406, 102, 15);
		frame.getContentPane().add(lblId_1_1_1);

		tfId = new JTextField();
		tfId.setEditable(false);
		tfId.setBounds(158, 323, 114, 19);
		frame.getContentPane().add(tfId);
		tfId.setColumns(10);

		tfPelicula = new JTextField();
		tfPelicula.setColumns(10);
		tfPelicula.setBounds(158, 350, 271, 19);
		frame.getContentPane().add(tfPelicula);

		tfTemporada = new JTextField();
		tfTemporada.setColumns(10);
		tfTemporada.setBounds(158, 377, 114, 19);
		frame.getContentPane().add(tfTemporada);

		tfcapitulos = new JTextField();
		tfcapitulos.setColumns(10);
		tfcapitulos.setBounds(158, 404, 114, 19);
		frame.getContentPane().add(tfcapitulos);

		JButton btnGuardar = new JButton("Guardar");

		btnGuardar.setBounds(447, 320, 117, 25);
		frame.getContentPane().add(btnGuardar);

		JButton btnActualizar = new JButton("Actualizar");

		btnActualizar.setBounds(447, 369, 117, 25);
		frame.getContentPane().add(btnActualizar);

		JButton btnBorrar = new JButton("Borrar");
		
		btnBorrar.setBounds(447, 416, 117, 25);
		frame.getContentPane().add(btnBorrar);
		tablaPeliculas.getTableHeader().setReorderingAllowed(false);
		rellenarTabla(modelPeliculas);
		tablaPeliculas.addMouseListener((MouseListener) new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = tablaPeliculas.getSelectedRow();
				TableModel model = tablaPeliculas.getModel();
				tfId.setText(model.getValueAt(index, 0).toString());
				tfPelicula.setText(model.getValueAt(index, 1).toString());
				tfTemporada.setText(model.getValueAt(index, 2).toString());
				tfcapitulos.setText(model.getValueAt(index, 3).toString());

			}
		});

		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Pelicula p = new Pelicula(tfPelicula.getText(), Integer.valueOf(tfTemporada.getText()),
						Integer.valueOf(tfcapitulos.getText()));
				dao.insertPelicula(p);
				rellenarTabla(modelPeliculas);
			}
		});

		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Pelicula p = new Pelicula(tfPelicula.getText(), Integer.valueOf(tfTemporada.getText()),
						Integer.valueOf(tfcapitulos.getText()));
				p.setId(Integer.valueOf(tfId.getText()));
				
				dao.updatePelicula(p);
				rellenarTabla(modelPeliculas);

			}
		});
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Pelicula p = new Pelicula(tfPelicula.getText(), Integer.valueOf(tfTemporada.getText()),
						Integer.valueOf(tfcapitulos.getText()));
				p.setId(Integer.valueOf(tfId.getText()));
				dao.deletePelicula(p.getId());
				rellenarTabla(modelPeliculas);


			}
		});
	}
}
