import React, {Component} from 'react';
import {List, message, Avatar, Spin} from 'antd';
import {inject, observer} from "mobx-react/index";
import reqwest from 'reqwest';
import InfiniteScroll from 'react-infinite-scroller';
import "./NodesInBuilding.css"
import {httpHead} from "../../Consts";

@inject(['UserData'])
@observer
class NodesInBuilding extends Component {
    constructor(props) {
        super(props);
    }

    state = {
        loading: false,
        hasMore: true,
    }

    getData = (callback) => {
        reqwest({
            url: httpHead + '',
            type: 'json',
            method: 'get',
            mode: 'cors',
            contentType: 'application/json',
            success: (res) => {
                callback(res);
            },
        });
    }

    componentDidMount() {
        this.getData((res) => {
            this.setState({
                data: res.results,
            });
        });
    }

    handleInfiniteOnLoad = () => {
        let data = this.state.data;
        this.setState({
            loading: true,
        });
        if (data.length > 14) {
            message.warning('Infinite List loaded all');
            this.setState({
                hasMore: false,
                loading: false,
            });
            return;
        }
        this.getData((res) => {
            data = data.concat(res.results);
            this.setState({
                data,
                loading: false,
            });
        });
    }

    getBuildingNodeList = async () => {
        const url = httpHead + '';
        try {
            const response = await fetch(url,
                {
                    method: "GET",
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    mode: 'cors',
                    body: this.UserData.currentBuilding.ID,
                });
            const json = await response.json();

            if (json.code === 200) {
                this.UserData.currentNoteList = json.nodeList;
            }
            else if (json.code === 404) {
            }
        }
        catch (e) {
            console.log(e)
        }
    }

    render() {
        return (
            <div className="demo-infinite-container">
                <InfiniteScroll
                    initialLoad={false}
                    pageStart={0}
                    loadMore={this.handleInfiniteOnLoad}
                    hasMore={!this.state.loading && this.state.hasMore}
                    useWindow={false}
                >
                    <List
                        dataSource={this.state.data}
                        renderItem={item => (
                            <List.Item key={item.id}>
                                <List.Item.Meta
                                    avatar={<Avatar
                                        src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"/>}
                                    title={<a href="https://ant.design">{item.name.last}</a>}
                                    description={item.email}
                                />
                                <div>Content</div>
                            </List.Item>
                        )}
                    >
                        {this.state.loading && this.state.hasMore && (
                            <div className="demo-loading-container">
                                <Spin/>
                            </div>
                        )}
                    </List>
                </InfiniteScroll>
            </div>

        )
    }
}

export default NodesInBuilding;
