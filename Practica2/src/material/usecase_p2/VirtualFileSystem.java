package material.usecase_p2;

import material.tree.narytree.LinkedTree;

public class VirtualFileSystem {
	
	private LinkedTree<String> fileSystem;
	
	public VirtualFileSystem() {
		fileSystem = new LinkedTree<String>();
	}
	
    public void loadFileSystem(String path){
    	
    }

    public String getFileSystem(){
        throw new RuntimeException("Not yet implemented");

    }


    public void moveFileById(int idFile, int idTargetFolder){
        throw new RuntimeException("Not yet implemented");
    }

    public void removeFileById(int idFile){
        throw new RuntimeException("Not yet implemented");
    }


    public Iterable<String> findBySubstring(int idStartFile, String substring){
        throw new RuntimeException("Not yet implemented");
    }

    public Iterable<String> findBySize(int idStartFile, long minSize, long maxSize){
        throw new RuntimeException("Not yet implemented");
    }

    public String getFileVirtualPath(int idFile){
        throw new RuntimeException("Not yet implemented");
    }

    public String getFilePath(int idFile){
        throw new RuntimeException("Not yet implemented");
    }
}
