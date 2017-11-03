package com.han.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class GenEntityMysql {
	
	public static void main(String[] args) {
		GenEntityMysql gem = new GenEntityMysql();
		gem.createAllPO("tq_rl", "root", "shumadianzi", "com.han.vo");
	}

	private String authorName = "���ٱ�";
	private static final String URL = "jdbc:mysql://182.254.214.63:3306/";
	private static final String DRIVER = "com.mysql.jdbc.Driver";


	public void createPO(String dbName, String tableName, String user, String password, String packageOutPath) {
		createPO(dbName, tableName, user, password, packageOutPath, true);
	}


	public void createPO(String dbName, String tableName, String user, String password, String packageOutPath,
			boolean ignore) {
		Connection con = null;
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			con = DriverManager.getConnection(URL + dbName, user, password);
			createPO(con, tableName, packageOutPath, ignore);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ����һ����
	 * 
	 * @param con
	 *            ���ݿ�����
	 * @param tableName
	 *            ����
	 * @param packageOutPath
	 *            �������?
	 * @param ignore
	 *            �Ƿ�����Ѵ��ڵ��࣬��Ϊtrue��ֱ�Ӹ��ǣ���Ϊfalse����Ҫ�ڿ���̨�ֶ������Ƿ񸲸�
	 */
	private void createPO(Connection con, String tableName, String packageOutPath, boolean ignore) {
		File directory = new File("");
		String outputPath = directory.getAbsolutePath() + "/src/" + checkPackage(packageOutPath).replace(".", "/") + "/"
				+ firstLetterUpper(tableName) + ".java";
		File outputFile = new File(outputPath);

		boolean isCover = true;
		if (!ignore) {
			if (outputFile.exists()) {
				System.out.println(firstLetterUpper(tableName) + ".java�Ѵ��ڣ��Ƿ񸲸�(Y)");
				Scanner input = new Scanner(System.in);
				String cover = input.nextLine();
				isCover = "Y".equalsIgnoreCase(cover);
			}
		}
		if (isCover) {
			// �жϰ����Ƿ����?
			if (!outputFile.getParentFile().exists()) {
				outputFile.getParentFile().mkdirs();
			}
			String[] colnames; // ��������
			String[] colTypes; // ������������
			int[] colSizes; // ������С����
			boolean f_util = false; // �Ƿ���Ҫ�����java.util.*
			// ��Ҫ����ʵ����ı�?
			String sql = "select * from `" + tableName + "`";
			PreparedStatement pStemt = null;
			PrintWriter pw = null;
			try {
				pStemt = con.prepareStatement(sql);
				ResultSetMetaData rsmd = pStemt.getMetaData();
				int size = rsmd.getColumnCount(); // ͳ����
				colnames = new String[size];
				colTypes = new String[size];
				colSizes = new int[size];
				for (int i = 0; i < size; i++) {
					colnames[i] = rsmd.getColumnName(i + 1);
					colTypes[i] = rsmd.getColumnTypeName(i + 1);

					if (colTypes[i].equalsIgnoreCase("timestamp") || colTypes[i].equalsIgnoreCase("datetime")
							|| colTypes[i].equalsIgnoreCase("date")) {
						f_util = true;
					}
					colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
				}

				String content = parse(tableName, colnames, colTypes, colSizes, packageOutPath, f_util);

				pw = new PrintWriter(new FileWriter(outputPath));
				pw.println(content);
				pw.flush();
			} catch (SQLException | IOException e) {
			} finally {
				if (pw != null) {
					pw.close();
				}
			}
		}
	}

	/**
	 * ������
	 * 
	 * @param tableName
	 *            ������
	 * @param colnames
	 *            ������
	 * @param colTypes
	 *            ������
	 * @param colSizes
	 *            �д�С
	 * @param packageOutPath
	 *            ����İ�·��?
	 * @param f_util
	 *            �Ƿ���Ҫutil��
	 * @param f_sql
	 *            �Ƿ���Ҫsql��
	 * @return ���ַ���
	 */
	private String parse(String tableName, String[] colnames, String[] colTypes, int[] colSizes, String packageOutPath,
			boolean f_util) {
		StringBuffer sb = new StringBuffer();

		sb.append("package " + checkPackage(packageOutPath) + ";\r\n");
		sb.append("\r\n");
		// �ж��Ƿ��빤�߰�
		if (f_util) {
			sb.append("import java.util.Date;\r\n");
		}
		sb.append("/**\n");
		sb.append(" * \n * " + tableName + " \r\n");
		sb.append(" * " + new Date() + "\r\n");
		sb.append(" * " + "@author " + authorName + " \r\n");
		sb.append(" * \n */ \r\n");

		sb.append("public class " + firstLetterUpper(tableName) + "{\r\n");
		sb.append(processAllAttrs(colnames, colTypes));
		sb.append(processDefaultConstructor(tableName));
		sb.append(processArgsConstructor(tableName, colnames, colTypes));
		sb.append(processToString(tableName, colnames));
		sb.append(processAllMethod(colnames, colTypes));
		sb.append("}\r\n");
		return sb.toString();
	}


	private String checkPackage(String packageOutPath){
		
		char[] ch = packageOutPath.trim().toCharArray();
		int start = 0;
		int count = ch.length;
		int end = ch.length;
		
		for (int i = 0; i < ch.length; i++) {
			if(ch[i]>='a' && ch[i]<='z'){
				start = i;
				break;
			}
		}
		for (int i = ch.length - 1; i > 0; i--) {
			if(ch[i]>='a' && ch[i]<='z'){
				end = i+1;
				break;
			}
		}
		count = end - start;
		System.out.println(new String(ch, start, count));
		return new String(ch, start, count);
	}
	
	/**
	 * �������е�����
	 * 
	 * @param colnames
	 *            ������
	 * @param colTypes
	 *            ������
	 * @return �����ַ���
	 */
	private String processAllAttrs(String[] colnames, String[] colTypes) {
		System.out.println("���������С�����");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < colnames.length; i++) {
			sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " " + firstLetterLower(colnames[i]) + ";\r\n");
		}
		return sb.toString();
	}

	private String processDefaultConstructor(String tableName) {
		StringBuffer sb = new StringBuffer();
		sb.append("\n\tpublic " + firstLetterUpper(tableName) + "(){\n");
		sb.append("\t\tsuper();\n");
		sb.append("\t}\n");
		return sb.toString();
	}

	private String processArgsConstructor(String tableName, String[] colnames, String[] colTypes) {
		StringBuffer sb = new StringBuffer();
		StringBuffer sbAttrs = new StringBuffer();
		sb.append("\n\tpublic " + firstLetterUpper(tableName) + "(");
		for (int i = 0; i < colTypes.length; i++) {
			String lowName = firstLetterLower(colnames[i]);
			sb.append(sqlType2JavaType(colTypes[i]));
			sb.append(" " + lowName + ",");

			sbAttrs.append("\t\tthis." + lowName + "=" + lowName + ";\n");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("){\n");
		sb.append("\t\tsuper();\n");

		sb.append(sbAttrs);

		sb.append("\t}\n");
		return sb.toString();
	}

	private String processToString(String tableName, String[] colnames) {
		StringBuffer sb = new StringBuffer();
		sb.append("\n\t@Override\n");
		sb.append("\tpublic String toString(){\n");
		sb.append("\t\treturn \"" + firstLetterUpper(tableName) + " [");
		for (int i = 0; i < colnames.length; i++) {
			String lowName = firstLetterLower(colnames[i]);
			sb.append(lowName + "=\" + " + lowName + " + \", ");
		}
		sb.delete(sb.length()-2,sb.length());
		sb.append("]\";\n");
		sb.append("\t}\n");
		return sb.toString();
	}

	private String processAllMethod(String[] colnames, String[] colTypes) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < colnames.length; i++) {
			String upName = firstLetterUpper(colnames[i]);
			String lowName = firstLetterLower(colnames[i]);
			sb.append("\n\tpublic void set" + upName + "(" + sqlType2JavaType(colTypes[i]) + " " + lowName + "){\r\n");
			sb.append("\t\tthis." + lowName + "=" + lowName + ";\r\n");
			sb.append("\t}\r\n");
			sb.append("\n\tpublic " + sqlType2JavaType(colTypes[i]) + " get" + upName + "(){\r\n");
			sb.append("\t\treturn " + lowName + ";\r\n");
			sb.append("\t}\r\n");
		}
		return sb.toString();
	}

	private String firstLetterUpper(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}

	private String firstLetterLower(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'A' && ch[0] <= 'Z') {
			ch[0] = (char) (ch[0] + 32);
		}
		return new String(ch);
	}

	private String sqlType2JavaType(String sqlType) {

		if (sqlType.equalsIgnoreCase("bit")) {
			return "boolean";
		} else if (sqlType.equalsIgnoreCase("tinyint")) {
			return "byte";
		} else if (sqlType.equalsIgnoreCase("smallint")) {
			return "short";
		} else if (sqlType.equalsIgnoreCase("int")) {
			return "int";
		} else if (sqlType.equalsIgnoreCase("bigint")) {
			return "long";
		} else if (sqlType.equalsIgnoreCase("float")) {
			return "float";
		} else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
				|| sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
				|| sqlType.equalsIgnoreCase("smallmoney") || sqlType.equalsIgnoreCase("double")) {
			return "double";
		} else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
				|| sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
				|| sqlType.equalsIgnoreCase("text") || sqlType.equalsIgnoreCase("varbinary")) {
			return "String";
		} else if (sqlType.equalsIgnoreCase("datetime")) {
			return "Date";
		} else if (sqlType.equalsIgnoreCase("image")) {
			return "Blod";
		} else if (sqlType.equalsIgnoreCase("timestamp")) {
			return "Date";
		}
		return null;
	}

	public void createAllPO(String dbName, String user, String password, String packageOutPath) {

		createAllPO(dbName, user, password, packageOutPath, true);

	}

	public void createAllPO(String dbName, String user, String password, String packageOutPath, boolean ignore) {
		Connection con = null;
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			con = DriverManager.getConnection(URL + dbName, user, password);
			DatabaseMetaData meta = con.getMetaData();
			ResultSet rs = meta.getTables(null, null, null, new String[] { "TABLE" });
			while (rs.next()) {
				String tableName = rs.getString(3);
				createPO(con, tableName, packageOutPath, ignore);
				
			}
		} catch (SQLException e) {
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}