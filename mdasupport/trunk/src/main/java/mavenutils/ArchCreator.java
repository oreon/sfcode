package mavenutils;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArchCreator {

	List<String> lstUnfilterables;
	List<String> lstUnfilterableFiles = new ArrayList<String>();
	StringWriter writer = new StringWriter();
	/*
	 * private String[][] arrDirFiles = { { "src/main/webapp/images", "*" }, {
	 * "src/main/webapp/css", "*" }, { "src/main/webapp/javaScript", "*" }, {
	 * "src/main/webapp/WEB-INF", "*" } };
	 */

	private static final String BASE_DIR = "C:/dev/workspace/richfacesSeamArchetype/src/main/resources/archetype-resources/";
	private String[][] arrDirFiles = { { "src/main/webapp", "*" },
			{ "src/main/resources", "*" }, { "src/etc", "*" }, {"src/model", "*"},
			{ "src/main/java", "*" }, { "src/test/java", "*" }
	};

	public static void main(String[] args) {
		ArchCreator archCreator = new ArchCreator();
		archCreator.run();
	}

	public ArchCreator() {
		String[] unfilterables = { "gif", "png", "css", "zip", "jpg", "bmp",
				"jsp", "tld","xhtml", "mdzip" };
		
		lstUnfilterables = Arrays.asList(unfilterables);
		lstUnfilterableFiles.add("src/main/resources/META-INF/components.xml");
	}

	public void run() {

		for (String[] dirFile : arrDirFiles) {

			File topLevelfile = new File(BASE_DIR + dirFile[0]);
			if (topLevelfile.isDirectory())
				createResNode(dirFile[0], topLevelfile);
			else
				System.out.println("Couldnt find directory "
						+ topLevelfile.getAbsolutePath());
		}
		
		log(writer.getBuffer().toString());

	}

	private void createResNode(String dirpath, File file) {
		String[] files = file.list();
		
		for (String fileName : files) {
			String filePath = dirpath + "/" + fileName;
			File child = new File(BASE_DIR + "/" + filePath);
			if (child.isDirectory()) {
				if (child.getName().startsWith(".")){
					//log("skipping directory " + child.getName());
				}
				else
					createResNode(filePath, child);
			} else {
				if (fileName.startsWith(".")){
					//log("skipping file " + fileName);
				}
				createNode("resource", dirpath + "/" + fileName, writer);
			}
		}
	}

	private void log(String string) {
		System.out.println(string);
	}

	/**
	 * @param nodeName
	 * @param arg
	 */
	private void createNode(String nodeName, String arg, StringWriter out) {
		
		String filterString = " filtered=\"false\"";
		boolean appendFilterString = false;
		String[] fileNameAndExt = arg.split("\\.");
		
		if (fileNameAndExt.length > 1) {
			if (lstUnfilterables.contains(fileNameAndExt[1])  || lstUnfilterableFiles.contains(arg))
				appendFilterString = true;
		}
		out.append("<" + nodeName + (appendFilterString ? filterString : "")
				+ ">\n");
		out.append(arg);
		out.append("\n</" + nodeName + ">\n");
	}

}
