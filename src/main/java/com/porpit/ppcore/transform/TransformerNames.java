package com.porpit.ppcore.transform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;

public class TransformerNames {
	public static boolean obfuscated = false;

	private static ArrayList<ClassName> classes = new ArrayList<ClassName>();
	private static ArrayList<MethodName> methods = new ArrayList<MethodName>();
	private static ArrayList<FieldName> fields = new ArrayList<FieldName>();

	static {
		try {
			InputStreamReader reader = new InputStreamReader(
					TransformerNames.class.getClassLoader().getResourceAsStream("assets/ppcore/transform/notch-mcp.srg"));
			BufferedReader bf = new BufferedReader(reader);
			String line = null;
			while ((line = bf.readLine()) != null) {
				String[] parts = line.split(" ");
				if (line.startsWith("CL:")) {
					if (parts.length == 3)
						classes.add(new ClassName(parts[2], parts[1]));
				} else if (line.startsWith("FD:")) {
					if (parts.length == 3) {
						String[] splitted = parts[2].split("/");
						String name = splitted[splitted.length - 1];
						splitted = parts[1].split("/");
						fields.add(new FieldName(name, splitted[1], splitted[0]));
					}
				} else if (line.startsWith("MD:")) {
					if (parts.length == 5) {
						String[] splitted = parts[3].split("/");
						String name = splitted[splitted.length - 1];
						splitted = parts[1].split("/");
						methods.add(new MethodName(name, splitted[1], splitted[0], parts[2]));
					}
				}
			}

			classes.sort(new Comparator<ClassName>() {

				@Override
				public int compare(ClassName arg0, ClassName arg1) {
					if (arg0.name.length() > arg1.name.length())
						return -1;
					if (arg0.name.length() == arg1.name.length())
						return 0;
					return 1;
				}

			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void emptyLists() {
		classes.clear();
		fields.clear();
		methods.clear();
	}

	public static String patchDESC(String desc) {
		if (obfuscated)
			for (int i = 0; i < classes.size(); i++)
				desc = desc.replace(classes.get(i).name, classes.get(i).obfname);
		return desc;
	}

	public static String patchClassName(String className) {
		if (obfuscated)
			for (int i = 0; i < classes.size(); i++)
				if (className.equals(classes.get(i).name))
					return classes.get(i).obfname;
		return className;
	}

	public static String patchFieldName(String fieldName, Class parent) {
		return patchFieldName(fieldName, patchClassName(parent.getName()));
	}

	public static String patchFieldName(String fieldName, String parentName) {
		if (obfuscated) {
			for (int i = 0; i < fields.size(); i++)
				if (fieldName.equals(fields.get(i).name) && parentName.equals(fields.get(i).parent))
					return fields.get(i).obfname;
		}
		return fieldName;
	}

	public static String patchMethodName(String methodName, String desc, Class parent) {
		return patchMethodName(methodName, desc, patchClassName(parent.getName()));
	}

	public static String patchMethodName(String methodName, String desc, String parentName) {
		if (obfuscated) {
			String obfdesc = patchDESC(desc);
			for (int i = 0; i < methods.size(); i++)
				if (methodName.equals(methods.get(i).name) && parentName.equals(methods.get(i).parent)
						&& methods.get(i).desc.equals(obfdesc))
					return methods.get(i).obfname;
		}
		return methodName;
	}

	private static class ClassName {
		public final String name;
		public final String obfname;

		public ClassName(String name, String obfname) {
			this.name = name;
			this.obfname = obfname;
		}
	}

	private static class FieldName extends ClassName {

		public FieldName(String name, String obfname, String parent) {
			super(name, obfname);
			this.parent = parent;
		}

		public final String parent;
	}

	private static class MethodName extends FieldName {

		public final String desc;

		public MethodName(String name, String obfname, String parent, String desc) {
			super(name, obfname, parent);
			this.desc = desc;
		}

	}

}
