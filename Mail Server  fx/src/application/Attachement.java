package application;

import java.io.Serializable;

public class Attachement implements Serializable  {
		String FileName;
		String FilePath;

		public void setPath(String path) {
			this.FilePath = path;
		}
		
		public void setName(String name) {
			this.FileName = name;
		}
		
		public String getPath() {
			return this.FilePath;
		}
		
		public String getName() {
			return this.FileName;
		}
}
