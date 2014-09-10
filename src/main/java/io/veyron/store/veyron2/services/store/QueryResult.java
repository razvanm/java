// This file was auto-generated by the veyron vdl tool.
// Source: service.vdl
package io.veyron.store.veyron2.services.store;

/**
 * type QueryResult struct{NestedResult veyron.io/store/veyron2/services/store.NestedResult int64;Name string;Fields map[string]any;Value any} 
 * QueryResult is a single level result.  If the query contained nested
 * queries, they will be sent as subsequent QueryResults.  If the
 * QueryResult is for a known type, the value is stored in Value.
 * If the result is for a dynamic type using selection, Fields will
 * be populated appropriately and Value will be nil.
 * 
 * Examples
 * 1) Return Team objects: "/teamsapp/teams".Query("*:Team")
 * QueryResult{
 * NestedResult: 0,
 * Name: "hornets",
 * Fields: nil,
 * Value: Team{Location: "CA", Mascot:"Buzz"},
 * },
 * QueryResult{
 * NestedResult: 0,
 * Name: "sharks",
 * Fields: nil,
 * Value: Team{Location: "NY", Mascot:"Jaws"},
 * }
 * 
 * 2) Return just the mascot names: "/teamsapp/teams".Query("*:Team{Mascot}")
 * QueryResult{
 * NestedResult: 0,
 * Name: "hornets",
 * Fields: {"Mascot": "Buzz"},
 * Value: nil,
 * },
 * QueryResult{
 * NestedResult: 0,
 * Name: "sharks",
 * Fields: {"Mascot": "Jaws"},
 * Value: nil
 * }
 * 
 * 3) Return the players for each team: "/teamsapp/teams".Query("*:Team{., players:Player")
 * QueryResult{
 * NestedResult: 0,
 * Name: "hornets",
 * Fields: {
 * ".": Team{Location: "CA", Mascot: "Buzz"},
 * Any subsequent QueryResult with a NestedResult of 1 should be
 * materialized in this field.
 * "players": NestedResult(1),
 * },
 * Value:nil
 * },
 * QueryResult{
 * NestedResult: 1,
 * Name: "John",
 * Fields: nil,
 * Value: Player{Age: 23, Hometown: "New York"},
 * },
 * QueryResult{
 * NestedResult: 1,
 * Name: "Julie",
 * Fields: nil,
 * Value: Player{Age: 24, Hometown: "Charlotte"},
 * },
 * QueryResult{
 * NestedResult: 0,
 * Name: "sharks",
 * Fields:
 * ".": Team{Location: "NY", Mascot: "Jaws"},
 * "players": NestedResult(2),
 * },
 * Value:nil
 * },
 * QueryResult{
 * NestedResult: 2,
 * Name: "Jamie",
 * Fields: nil,
 * Value: Player{Age: 20, Hometown: "Seattle"},
 * },
 * QueryResult{
 * NestedResult: 2,
 * Name: "Jacob",
 * Fields: nil,
 * Value: Player{Age: 25, Hometown: "Miami"},
 * }
 * 
 * Nested results may in turn contain their own nested results.  A
 * NestedResult value is always greater than the parent's NestedResult value.
 * In a stream of results, a decrease in the NestedResult values means
 * that the group is finished.  Here is an example result stream containing
 * only the NestedResult values.  The parentheses show the grouping.
 * (0 (1 (2, 2), 1 (3), 1 (4, 4, 4)), (0 (1, 1 (5, 5)))
 **/
public final class QueryResult implements android.os.Parcelable, java.io.Serializable {
    static final long serialVersionUID = 0L;

    
    
      @com.google.gson.annotations.SerializedName("NestedResult")
      private io.veyron.store.veyron2.services.store.NestedResult nestedResult;
    
      @com.google.gson.annotations.SerializedName("Name")
      private java.lang.String name;
    
      @com.google.gson.annotations.SerializedName("Fields")
      private java.util.Map<java.lang.String, com.veyron2.vdl.Any> fields;
    
      @com.google.gson.annotations.SerializedName("Value")
      private com.veyron2.vdl.Any value;
    

    
    public QueryResult(final io.veyron.store.veyron2.services.store.NestedResult nestedResult, final java.lang.String name, final java.util.Map<java.lang.String, com.veyron2.vdl.Any> fields, final com.veyron2.vdl.Any value) {
        
            this.nestedResult = nestedResult;
        
            this.name = name;
        
            this.fields = fields;
        
            this.value = value;
        
    }

    
    
    public io.veyron.store.veyron2.services.store.NestedResult getNestedResult() {
        return this.nestedResult;
    }
    public void setNestedResult(io.veyron.store.veyron2.services.store.NestedResult nestedResult) {
        this.nestedResult = nestedResult;
    }
    
    public java.lang.String getName() {
        return this.name;
    }
    public void setName(java.lang.String name) {
        this.name = name;
    }
    
    public java.util.Map<java.lang.String, com.veyron2.vdl.Any> getFields() {
        return this.fields;
    }
    public void setFields(java.util.Map<java.lang.String, com.veyron2.vdl.Any> fields) {
        this.fields = fields;
    }
    
    public com.veyron2.vdl.Any getValue() {
        return this.value;
    }
    public void setValue(com.veyron2.vdl.Any value) {
        this.value = value;
    }
    

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        final QueryResult other = (QueryResult)obj;

        
        
        
        if (this.nestedResult == null) {
            if (other.nestedResult != null) {
                return false;
            }
        } else if (!this.nestedResult.equals(other.nestedResult)) {
            return false;
        }
         
         
        
        
        
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
         
         
        
        
        
        if (this.fields == null) {
            if (other.fields != null) {
                return false;
            }
        } else if (!this.fields.equals(other.fields)) {
            return false;
        }
         
         
        
        
        
        if (this.value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!this.value.equals(other.value)) {
            return false;
        }
         
         
         
        return true;
    }
    @Override
    public int hashCode() {
        int result = 1;
        final int prime = 31;
        
        result = prime * result + (nestedResult == null ? 0 : nestedResult.hashCode());
        
        result = prime * result + (name == null ? 0 : name.hashCode());
        
        result = prime * result + (fields == null ? 0 : fields.hashCode());
        
        result = prime * result + (value == null ? 0 : value.hashCode());
        
        return result;
    }
    @Override
    public int describeContents() {
    	return 0;
    }
    @Override
    public void writeToParcel(android.os.Parcel out, int flags) {
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, nestedResult);
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, name);
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, fields);
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, value);
    	
    }
	public static final android.os.Parcelable.Creator<QueryResult> CREATOR
		= new android.os.Parcelable.Creator<QueryResult>() {
		@Override
		public QueryResult createFromParcel(android.os.Parcel in) {
			return new QueryResult(in);
		}
		@Override
		public QueryResult[] newArray(int size) {
			return new QueryResult[size];
		}
	};
	private QueryResult(android.os.Parcel in) {
		
			this.nestedResult = (io.veyron.store.veyron2.services.store.NestedResult) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.nestedResult);
		
			this.name = (java.lang.String) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.name);
		
			this.fields = (java.util.Map<java.lang.String, com.veyron2.vdl.Any>) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.fields);
		
			this.value = (com.veyron2.vdl.Any) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.value);
		
	}
}