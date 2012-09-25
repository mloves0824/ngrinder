/*
 * Copyright (C) 2012 - 2012 NHN Corporation
 * All rights reserved.
 *
 * This file is part of The nGrinder software distribution. Refer to
 * the file LICENSE which is part of The nGrinder distribution for
 * licensing details. The nGrinder distribution is available on the
 * Internet at http://nhnopensource.org/ngrinder
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.ngrinder.chart.service;

import java.util.Set;

import org.ngrinder.agent.model.AgentInfo;
import org.ngrinder.chart.repository.MonitorDataRepository;
import org.ngrinder.monitor.controller.MonitorExecuteManager;
import org.ngrinder.monitor.controller.domain.MonitorAgentInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Agent monitor data service.
 * 
 * @author Tobi
 * @since 3.0
 */
@Service
public class MonitorAgentService {

	private static final Logger LOG = LoggerFactory.getLogger(MonitorAgentService.class);

	@Autowired
	private MonitorDataRepository monitorDataRepository;

	/**
	 * add a set of agents to the monitor manager, and start the monitor job. 
	 * @param key
	 * @param agents
	 */
	public void addMonitor(String key, Set<AgentInfo> agents) {
		for (AgentInfo agent : agents) {
			MonitorAgentInfo monitorAgentInfo = 
					MonitorAgentInfo.getSystemMonitor(agent.getIp(), agent.getPort(), monitorDataRepository);
			MonitorExecuteManager.getInstance().addAgentMonitor(agent.getIp(), monitorAgentInfo);
		}
		LOG.debug("Init nGrinder Monitor for:{} successfully.", key);
	}

	/**
	 * Remove agents, and stop monitoring.
	 * 
	 * @param agent
	 */
	public void removeMonitorAgents(String agentIP) {
		MonitorExecuteManager.getInstance().removeAgentMonitor(agentIP);
		LOG.debug("Remove nGrinder Monitor for:{} successfully.", agentIP);
	}

}