package com.manuscript.rest.api;

import com.manuscript.rest.forms.request.InvitationRequestRequest;
import com.manuscript.rest.forms.response.InvitationRequestResponse;
import com.manuscript.rest.service.IInvitationRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.manuscript.rest.common.Constants.RESOURCE_INVITATION;

@RestController
@RequestMapping(RESOURCE_INVITATION)
@CrossOrigin()
public class InvitationRequestController {
    private final IInvitationRequestService requestService;

    public InvitationRequestController(IInvitationRequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("/createInvitation")
    public ResponseEntity<List<InvitationRequestResponse>> createInvitation(@RequestBody InvitationRequestRequest invitationRequestRequest) {
        return ResponseEntity.ok(requestService.save(invitationRequestRequest));
    }

    @GetMapping("/approveInvitationRequest/{email}")
    public ResponseEntity<List<InvitationRequestResponse>> approveInvitationRequest(@PathVariable String email) {
        if (email.isEmpty()) {
            return ResponseEntity.ok(requestService.getAllInvitations());
        }
        return ResponseEntity.ok(requestService.approveRequest(email));
    }

    @GetMapping("/denyInvitationRequest/{email}")
    public ResponseEntity<List<InvitationRequestResponse>> denyInvitationRequest(@PathVariable String email) {
        if (email.isEmpty()) {
            return ResponseEntity.ok(requestService.getAllInvitations());
        }
        return ResponseEntity.ok(requestService.denyRequest(email));
    }

    @GetMapping("/getAllInvitations")
    public ResponseEntity<List<InvitationRequestResponse>> getAllInvitations() {
        return ResponseEntity.ok(requestService.getAllInvitations());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
