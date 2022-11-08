# Plan
We followed the detailed description in the project specification, without any other plans.

# Report
## Non-functional Test Suite 
According to the specifications, we applied two CRUD operations to two entity types: createOwner, updateOwner, createPet, updatePet, and we evaluated their performance from three perspectives: runtime, CPU usage and memory usage. We used Windows Perfmon to track the percentage of CPU and available free memory. For each operation, we collected data of both 500 and 1000 instances through csv files. The charts in Analysis section only show the performance of 1000 instances. In order to compare the system under different loads, we also calculated the average transaction time, CPU usage and memory usage with both 500 and 1000 transactions, as shown in the table below.

#### Link to the source code files: [NonFunctionalTest.java](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/blob/master/src/test/java/org/springframework/samples/petclinic/nonfunctional/NonFunctionalTest.java) 

## Comparison Statistics: 500 vs 1000 instances
| CRUD | Number of instances | Average Transaction Time(ms) | Average CPU usage(%) | Average Memory Usage(bytes) |
|:------------------------:|:----------------------:|:----------------------------:|:--------------:|:---------------------------:|
|        createOwner       |           500          |      11.396                |  0.720      |       2.917            |
|        createOwner       |          1000          |          9.657          |  1.832  |          4.594           |
|        updateOwner       |           500          |      9.804                |   1.719     |     5.322                  |
|        updateOwner       |          1000          |             10.862           |     1.746      |       2.189                |
|         createPet        |           500          |       23.634          |   1.520      |         1.544          |
|         createPet        |          1000          |          37.722      |     1.127     |       3.119              |
|         updatePet        |           500          |       23.316             |   1.315      |           2.841          |
|         updatePet        |          1000          |          38.435        |   1.168      |    4.611                 |

## Runtime Analysis
> All graphs are based on 1000 instances.

x axis: number of instances, y axis: transaction time (ms)
* **Create Owner**
![time_createowner](https://user-images.githubusercontent.com/48535851/200147330-20d7e30f-b251-42f2-96e4-c4e16bcd99d4.png)
* **Update Owner**
![time_updateowner](https://user-images.githubusercontent.com/48535851/200147352-05285988-0c94-4cfb-b49a-6a0debab5bb8.png)

* **Create Pet**
![time_createpet](https://user-images.githubusercontent.com/48535851/200147364-42f2db6e-498b-4ed7-82f1-073155866c90.png)

* **Update Pet**
![time_updatepet](https://user-images.githubusercontent.com/48535851/200147370-338f76c3-fce0-4f4f-9321-99255086d696.png)

### Analysis
According to the graphs above, the effect of entity type on runtime is significant, with the average runtime for owner operations around 10 ms and for pet operations around 40 ms. While the type of CRUD operation does not have much effect, meaning that the difference between create operations and update operations is small. It is interesting to note that the runtime of owner is independent of the number of instances, while the runtime of pet tends to increase as the number of instances increases, which seems to be some sort of positive relationship. In all operations, there exist some peak values, which is normal.


## CPU usage
> All graphs are based on 1000 instances.

x axis: elapsed time (ms), y axis: CPU percent usage (%)
* **Create Owner**
![cpu_createowner](https://user-images.githubusercontent.com/48535851/200147382-bee19b92-68aa-43ba-9b28-d0180577c0d3.png)

* **Update Owner**
![cpu_updateowner](https://user-images.githubusercontent.com/48535851/200147386-7ea73047-0f6b-4102-867b-e9f1e73eebf2.png)

* **Create Pet**
![cpu_createpet](https://user-images.githubusercontent.com/48535851/200147398-221a9a47-4c92-4cee-9498-d7d4f7acc39f.png)

* **Update Pet**
![cpu_updatepet](https://user-images.githubusercontent.com/48535851/200147405-1faf8654-6009-42f2-b1b7-a5760ec2ab60.png)

### Analysis
For CPU usage, when elapsed time increases, there is not much difference between both CRUD operations among these two entities. CPU usage of owner operations stays around 1.25%, and CPU usage of pet operations fluctuates around 1%. For now, no regular pattern was clearly observed.

## Memory Usage
> All graphs are based on 1000 instances.

x axis: elapsed time (ms), y axis: available free memory (bytes)
* **Create Owner**
![mem_createowner](https://user-images.githubusercontent.com/48535851/200147416-d220d712-6b69-4d71-9966-e72efd6882b4.png)

* **Update Owner**
![mem_updateowner](https://user-images.githubusercontent.com/48535851/200147423-dcc6fcab-7d2b-4574-b80d-db44d9039785.png)

* **Create Pet**
![mem_createpet](https://user-images.githubusercontent.com/48535851/200147430-02b4f9b6-e2a6-40d2-808b-b41cb909508c.png)

* **Update Pet**
![mem_updatepet](https://user-images.githubusercontent.com/48535851/200147435-6568bc5c-0ec9-4fc2-b897-c61f08f2ec2d.png)

### Analysis
The average memory usage for these two operations is both around three bytes among owner and pet. And their trends are almost the same. For create operations, memory usage increases when elapsed time increases. For update operations, there is a sudden drop when memory usage increases to a peak, which should be due to the memory release.

## Performance Risk
* In terms of runtime, there exist several peak values for all operations, which can be a potential risk. However, the frequency of such exceptions is quite low and the overall average transaction time is almost unaffected, keeping in the order of 10 milliseconds. Hence, it won't affect the overall performance of the system.
* In terms of CPU usage, when the number of instances increased from 500 to 1000, the CPU usage for creating owners also doubled. This can be a serious problem if the number of pets is very large, as high CPU usage can cause the system to run slowly or even crash. However, for a normal petclinic, the number of pets is fairly limited, so this should not be a threat.
* In terms of memory usage, when the number of instances increases, memory usage increases as well. But even so, memory usage is still quite low because the actual number of pets in a petclinic is limited, just as mentioned before.

In summary, the system doesn't have significant risks and can be used with confidence.
