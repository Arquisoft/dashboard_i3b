package asw.controller;

import asw.model.Proposal;
import asw.repository.DBService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by guille on 3/30/17.
 */

@Controller
public class DashboardController {
    private final DBService service;

    private static final Logger logger = Logger.getLogger(DashboardController.class);

    @Autowired
    DashboardController(DBService service) {
        this.service = service;
        createMockDatabaseContent();

    }

    private void createMockDatabaseContent() {
        service.insertProposal(new Proposal("Test"));
        service.insertProposal(new Proposal("Test2"));
        service.insertProposal(new Proposal("Test create"));
    }

    @RequestMapping(path = "/councilstaff")
    public String dashboardCouncilstaffController(Model model) {
        model.addAttribute("proposals", service.getAllProposal());
        return "councilstaff";
    }

    @RequestMapping(path = "/councilmen")
    public String dashboardCouncilmenController(Model model) {
        model.addAttribute("proposals", service.getAllProposal());
        return "councilmen";
    }

    @RequestMapping(path = "/otherAuthorities")
    public String dashboardOtherAuthoritiesController(Model model) {
        model.addAttribute("proposals", service.getAllProposal());
        return "otherAuthorities";
    }
}
