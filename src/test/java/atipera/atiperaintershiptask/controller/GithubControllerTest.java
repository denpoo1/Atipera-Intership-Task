package atipera.atiperaintershiptask.controller;

import static org.mockito.Mockito.when;

import atipera.atiperaintershiptask.dto.response.feign_client.GithubBranchResponse;
import atipera.atiperaintershiptask.dto.response.feign_client.GithubOwnerResponse;
import atipera.atiperaintershiptask.dto.response.feign_client.GithubRepositoryResponse;
import atipera.atiperaintershiptask.feign_client.GithubClient;
import atipera.atiperaintershiptask.service.GithubService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {GithubController.class, GithubService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class GithubControllerTest {
        @MockBean
        private GithubClient githubClient;

        @Autowired
        private GithubController githubController;

        @Test
        void testGetAllReposByUsername2() throws Exception {
                // Arrange
                GithubOwnerResponse owner = new GithubOwnerResponse();
                owner.setLogin("Login");

                GithubRepositoryResponse githubRepositoryResponse = new GithubRepositoryResponse();
                githubRepositoryResponse.setFork(true);
                githubRepositoryResponse.setName("Name");
                githubRepositoryResponse.setOwner(owner);

                ArrayList<GithubRepositoryResponse> githubRepositoryResponseList = new ArrayList<>();
                githubRepositoryResponseList.add(githubRepositoryResponse);
                Optional<List<GithubRepositoryResponse>> ofResult = Optional.of(githubRepositoryResponseList);
                when(githubClient.getAllRepositoriesByUsername(Mockito.<String>any())).thenReturn(ofResult);
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/github/{username}/repos",
                        "janedoe");

                // Act and Assert
                MockMvcBuilders.standaloneSetup(githubController)
                        .build()
                        .perform(requestBuilder)
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                        .andExpect(MockMvcResultMatchers.content().string("[]"));
        }

        @Test
        void testGetAllReposByUsername3() throws Exception {
                // Arrange
                GithubOwnerResponse owner = new GithubOwnerResponse();
                owner.setLogin("Login");

                GithubRepositoryResponse githubRepositoryResponse = new GithubRepositoryResponse();
                githubRepositoryResponse.setFork(true);
                githubRepositoryResponse.setName("Name");
                githubRepositoryResponse.setOwner(owner);

                GithubOwnerResponse owner2 = new GithubOwnerResponse();
                owner2.setLogin("atipera.atiperaintershiptask.dto.response.feign_client.GithubOwnerResponse");

                GithubRepositoryResponse githubRepositoryResponse2 = new GithubRepositoryResponse();
                githubRepositoryResponse2.setFork(false);
                githubRepositoryResponse2
                        .setName("atipera.atiperaintershiptask.dto.response.feign_client.GithubRepositoryResponse");
                githubRepositoryResponse2.setOwner(owner2);

                ArrayList<GithubRepositoryResponse> githubRepositoryResponseList = new ArrayList<>();
                githubRepositoryResponseList.add(githubRepositoryResponse2);
                githubRepositoryResponseList.add(githubRepositoryResponse);
                Optional<List<GithubRepositoryResponse>> ofResult = Optional.of(githubRepositoryResponseList);
                Optional<List<GithubBranchResponse>> ofResult2 = Optional.of(new ArrayList<>());
                when(githubClient.getAllBranchesByUsernameAndProjectName(Mockito.<String>any(), Mockito.<String>any()))
                        .thenReturn(ofResult2);
                when(githubClient.getAllRepositoriesByUsername(Mockito.<String>any())).thenReturn(ofResult);
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/github/{username}/repos",
                        "janedoe");

                // Act and Assert
                MockMvcBuilders.standaloneSetup(githubController)
                        .build()
                        .perform(requestBuilder)
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                        .andExpect(MockMvcResultMatchers.content()
                                .string(
                                        "[{\"repositoryName\":\"atipera.atiperaintershiptask.dto.response.feign_client.GithubRepositoryResponse"
                                                + "\",\"ownerLogin\":\"atipera.atiperaintershiptask.dto.response.feign_client.GithubOwnerResponse\",\"branches"
                                                + "\":[]}]"));
        }

        @Test
        void testGetAllReposByUsername4() throws Exception {
                // Arrange
                GithubOwnerResponse owner = new GithubOwnerResponse();
                owner.setLogin("Login");

                GithubRepositoryResponse githubRepositoryResponse = new GithubRepositoryResponse();
                githubRepositoryResponse.setFork(false);
                githubRepositoryResponse.setName("Name");
                githubRepositoryResponse.setOwner(owner);

                GithubOwnerResponse owner2 = new GithubOwnerResponse();
                owner2.setLogin("atipera.atiperaintershiptask.dto.response.feign_client.GithubOwnerResponse");

                GithubRepositoryResponse githubRepositoryResponse2 = new GithubRepositoryResponse();
                githubRepositoryResponse2.setFork(false);
                githubRepositoryResponse2
                        .setName("atipera.atiperaintershiptask.dto.response.feign_client.GithubRepositoryResponse");
                githubRepositoryResponse2.setOwner(owner2);

                ArrayList<GithubRepositoryResponse> githubRepositoryResponseList = new ArrayList<>();
                githubRepositoryResponseList.add(githubRepositoryResponse2);
                githubRepositoryResponseList.add(githubRepositoryResponse);
                Optional<List<GithubRepositoryResponse>> ofResult = Optional.of(githubRepositoryResponseList);
                Optional<List<GithubBranchResponse>> ofResult2 = Optional.of(new ArrayList<>());
                when(githubClient.getAllBranchesByUsernameAndProjectName(Mockito.<String>any(), Mockito.<String>any()))
                        .thenReturn(ofResult2);
                when(githubClient.getAllRepositoriesByUsername(Mockito.<String>any())).thenReturn(ofResult);
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/github/{username}/repos",
                        "janedoe");

                // Act and Assert
                MockMvcBuilders.standaloneSetup(githubController)
                        .build()
                        .perform(requestBuilder)
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                        .andExpect(MockMvcResultMatchers.content()
                                .string(
                                        "[{\"repositoryName\":\"atipera.atiperaintershiptask.dto.response.feign_client.GithubRepositoryResponse\""
                                                + ",\"ownerLogin\":\"atipera.atiperaintershiptask.dto.response.feign_client.GithubOwnerResponse\",\"branches"
                                                + "\":[]},{\"repositoryName\":\"Name\",\"ownerLogin\":\"Login\",\"branches\":[]}]"));
        }

        @Test
        void testGetAllReposByUsername5() throws Exception {
                // Arrange
                GithubOwnerResponse owner = new GithubOwnerResponse();
                owner.setLogin("Login");

                GithubRepositoryResponse githubRepositoryResponse = new GithubRepositoryResponse();
                githubRepositoryResponse.setFork(true);
                githubRepositoryResponse.setName("Name");
                githubRepositoryResponse.setOwner(owner);

                GithubOwnerResponse owner2 = new GithubOwnerResponse();
                owner2.setLogin("atipera.atiperaintershiptask.dto.response.feign_client.GithubOwnerResponse");

                GithubRepositoryResponse githubRepositoryResponse2 = new GithubRepositoryResponse();
                githubRepositoryResponse2.setFork(false);
                githubRepositoryResponse2
                        .setName("atipera.atiperaintershiptask.dto.response.feign_client.GithubRepositoryResponse");
                githubRepositoryResponse2.setOwner(owner2);

                ArrayList<GithubRepositoryResponse> githubRepositoryResponseList = new ArrayList<>();
                githubRepositoryResponseList.add(githubRepositoryResponse2);
                githubRepositoryResponseList.add(githubRepositoryResponse);
                Optional<List<GithubRepositoryResponse>> ofResult = Optional.of(githubRepositoryResponseList);
                Optional<List<GithubBranchResponse>> ofResult2 = Optional.of(new ArrayList<>());
                when(githubClient.getAllBranchesByUsernameAndProjectName(Mockito.<String>any(), Mockito.<String>any()))
                        .thenReturn(ofResult2);
                when(githubClient.getAllRepositoriesByUsername(Mockito.<String>any())).thenReturn(ofResult);
                SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders.formLogin();

                // Act
                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(githubController)
                        .build()
                        .perform(requestBuilder);

                // Assert
                actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
        }
}
