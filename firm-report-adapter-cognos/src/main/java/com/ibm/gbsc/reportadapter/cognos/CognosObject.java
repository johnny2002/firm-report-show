 package com.ibm.gbsc.reportadapter.cognos;
 
 public class CognosObject
 {
   private String id;
   private String name;
   private String type;
   private String path;
   private boolean leaf;
   private CognosObject[] child;
 
   public String getName()
   {
     return this.name;
   }
 
   public void setName(String name)
   {
     this.name = name;
   }
 
   public String getType()
   {
     return this.type;
   }
 
   public void setType(String type)
   {
     this.type = type;
   }
 
   public String getPath()
   {
     return this.path;
   }
 
   public void setPath(String path)
   {
     this.path = path;
   }
 
   public boolean isLeaf()
   {
     return this.leaf;
   }
 
   public void setLeaf(boolean leaf)
   {
     this.leaf = leaf;
   }
 
   public void setId(String id)
   {
     this.id = id;
   }
 
   public String getId()
   {
     return this.id;
   }
 
   public void setChild(CognosObject[] child)
   {
     this.child = child;
   }
 
   public CognosObject[] getChild()
   {
     return this.child;
   }
   public String toXmlString() {
     StringBuffer sb = new StringBuffer("<co leaf=\"").append(isLeaf()).append("\" ");
     sb.append("id=\"").append(getId()).append("\" ");
     sb.append("n=\"").append(getName()).append("\" ");
     sb.append("t=\"").append(getType()).append("\" ");
     sb.append("p=\"").append(getPath()).append("\" ");
     sb.append(">");
     if ((this.child != null) && (this.child.length > 0)) {
       for (CognosObject o : this.child) {
         sb.append(o.toXmlString());
       }
     }
     sb.append("</co>");
     return sb.toString();
   }
 }
