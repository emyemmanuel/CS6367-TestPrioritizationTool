package test.pack;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class StatementCoverageData
{
	static String key;
	static int hashSize;
	static Map<String, Set<String>> h = new HashMap<>();
	static final HashMap<String, Integer> hMap = new HashMap<>();
	static final HashMap<String, Set<String>> hTemp = new HashMap<>();
	static final Set<String> set = new HashSet<String>();
	static ValueComparator bvc = new ValueComparator(hMap);
	static TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(bvc);
	static List<String> list = new ArrayList<String>();
	static List<String> list1 = new ArrayList<String>();
    static List<String> list2 = new ArrayList<String>();

	public static void testExecuted(String className, String methodName)
    {
		key = className;
		h.put(key, new HashSet<String>());
		hTemp.put(key, new HashSet<String>());
	}

	public static void lineExecuted(String lineName)
    {
		String str = lineName;
		if (h.containsKey(key))
        {
			h.get(key).add(str);
			hTemp.get(key).add(str);
		}
	}
    
    /* Obsolete code
    public static void writeIntoFile()
    {
        try
        {
            File file = new File("stmt-cov.txt");
            if (file.exists())
                file.delete();
            else
                file.createNewFile();
            FileWriter writer = new FileWriter("stmt-cov.txt",true);
            int count;
            
            //
            Map<String,Integer> priorityHash = new HashMap<>();
            
            
            for(Map.Entry<String, Set<String>> e : h.entrySet())
            {
                writer.write(e.getKey());
                writer.write(System.getProperty("line.separator"));
                count=0;
                String s[]=e.getKey().split(" ");
                String result=s[1];
                String part[]=result.split(":");
                String newKey=part[0];
                for(String temp : e.getValue())
                {
                    writer.write(temp);
                    count++;
                    writer.write(System.getProperty("line.separator"));
                }
                if(priorityHash.containsKey(newKey)){
                    count= count +priorityHash.get(newKey);
                    priorityHash.put(newKey, count);
                }
                else
                    priorityHash.put(newKey, count);
                writer.write("[Priority]:"+count);
                writer.write(System.getProperty("line.separator"));
            }
            writer.close();
            //sort priority hash map
            Map<String,Integer> sortedPriority = new HashMap<>();
            sortedPriority=Sort.sortMapByValues(priorityHash);
            
            File file1 = new File("priority-test-list.txt");
            if (file1.exists())
                file1.delete();
            else
                file1.createNewFile();
            FileWriter writer1 = new FileWriter("priority-test-list.txt",true);
            for(Map.Entry<String, Integer> a : sortedPriority.entrySet())
            {
                writer1.write(a.getKey());
                writer1.write(System.getProperty("line.separator"));
            }
            writer1.close();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
     */
    
    
    /*
     *Total test prioritization
     *sortByComparator()- Test Classes sorted based on the statement coverage.
     */
	public static List<String>  writeTotalIntoFile()
    {
		try
        {
			File file = new File("total.txt");
			if (file.exists())
				file.delete();
			else
				file.createNewFile();
			FileWriter writer = new FileWriter("total.txt", true);
			h=sortByComparator(h,false);
			for (String key : h.keySet())
            {
                list1.add(key);
                writer.write("" + key); //+ " " + h.get(key).size());
				writer.write(System.getProperty("line.separator"));
			}
			writer.close();
            return list1;
		} catch (Exception ex)
        {
			ex.printStackTrace();
             return null;
		}
	}
    
    /*
     *Additional test prioritization
     *Test classes sorted based on statement coverage
     *getPriority()- the priority is found for a class the redundant statements covered by
      other classes are removed from the hash map with the list of test classes
     *sortByComparator()- Test classes sorted again based on new priority
     *returns the sorted list of test class names based on additional prioritization
     */
    
    public static List<String> writeAdditionalIntoFile()
    {
        try
        {
            File file = new File("additional.txt");
            if (file.exists())
                file.delete();
            else
                file.createNewFile();
            FileWriter writer = new FileWriter("additional.txt", true);
            while(h.size()>0)
            {
                getPriority();
                h=sortByComparator(h, false);
            }
            //writing TestClass name to file
            for(String line:list)
            {
                list2.add(line);
                writer.write(line);
                writer.write(System.getProperty("line.separator"));
            }
            writer.close();
            return list2;
        } catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    /*
     *Assigning new priority to test classes after removing the redundant statement 
      coverage from the removed test class
     */
	
	private static void getPriority()
    {
        Set<String> currentSet;
        Iterator<String> strIt = h.keySet().iterator();
        
        if (strIt.hasNext())
        {
        	String name = strIt.next();
            currentSet = h.get(name);
            set.addAll(currentSet);
            list.add(name);
            h.remove(name);
        }
        Iterator<Entry<String, Set<String>>> iterate = h.entrySet().iterator();
        while (iterate.hasNext())
        {
            currentSet = iterate.next().getValue();
            currentSet.removeAll(set);
        }
    }
	
    /*
     *Sort map based on value
     *Keep the sorted Test class list in a linked hash map rather than hashmap where the 
      order is not maintained
     */
	private static Map<String, Set<String>> sortByComparator(Map<String, Set<String>> unsortMap, final boolean order)
    {
		List<Entry<String,Set<String>>> list = new LinkedList<Entry<String, Set<String>>>(unsortMap.entrySet());
		Collections.sort(list, new Comparator<Entry<String, Set<String>>>()
        {
			public int compare(Entry<String, Set<String>> o1, Entry<String,Set<String>> o2)
            {
				Integer val1 = (Integer) o1.getValue().size();
				Integer val2 = (Integer) o2.getValue().size();
				if (order)
                {
					return val1.compareTo(val2);
				} else
                {
					return val2.compareTo(val1);
				}
			}
		});
		Map<String, Set<String>> sortedMap = new LinkedHashMap<String,Set<String>>();
		for (Entry<String, Set<String>> entry : list)
        {
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
}

class ValueComparator implements Comparator<String>
{
	HashMap<String, Integer> base;

	public ValueComparator(HashMap<String, Integer> base)
    {
		this.base = base;
	}

	public int compare(String a, String b)
    {
		if (base.get(a) >= base.get(b))
			return -1;
		else
			return 1;
	}
}
