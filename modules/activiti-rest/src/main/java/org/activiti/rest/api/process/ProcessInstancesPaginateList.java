/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.rest.api.process;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.rest.api.AbstractPaginateList;
import org.activiti.rest.api.ActivitiUtil;

/**
 * @author Tijs Rademakers
 */
public class ProcessInstancesPaginateList extends AbstractPaginateList {

  @SuppressWarnings("rawtypes")
  @Override
  protected List processList(List list) {
    List<ProcessInstancesResponse> processResponseList = new ArrayList<ProcessInstancesResponse>();
    for (Object instance : list) {
      HistoricProcessInstance hinstance = (HistoricProcessInstance) instance;
      ProcessInstancesResponse processInstanceResponse = new ProcessInstancesResponse(hinstance);
      
      boolean processStillActive = hinstance.getEndTime() == null;
      
      if (processStillActive) {
        ProcessInstance processInstance = ActivitiUtil.getRuntimeService().createProcessInstanceQuery()
          .processInstanceId(hinstance.getId()).singleResult();
        processInstanceResponse.setSuspended(processInstance.isSuspended());
      }
      
      processResponseList.add(processInstanceResponse);
    }
    return processResponseList;
  }
}
